package com.numble.reservation.service.reservation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.numble.reservation.client.PaymentGatewayApiClient;
import com.numble.reservation.client.dto.PaymentCancelRequest;
import com.numble.reservation.client.dto.PaymentCompleteRequest;
import com.numble.reservation.client.dto.PaymentCompleted;
import com.numble.reservation.code.VenueSeatType;
import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.Performance;
import com.numble.reservation.domain.Reservation;
import com.numble.reservation.domain.ReservationSeat;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.entity.reservation.ReservationEntity;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;
import com.numble.reservation.persistence.repository.reservation.ReservationRepository;
import com.numble.reservation.service.performance.PerformanceCapacityService;
import com.numble.reservation.service.reservation.dto.ReservationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final PerformanceRepository performanceRepository;
	private final ReservationTotalPriceCalculatorService reservationTotalPriceCalculatorService;
	private final ReservationValidationService reservationValidationService;
	private final PerformanceCapacityService performanceCapacityService;
	private final PaymentGatewayApiClient paymentGatewayApiClient;
	private final ApplicationEventPublisher eventPublisher;

	private final RedissonClient redisson;
	private final ApplicationContext applicationContext;

	public Reservation reserve(Long memberId, Long performanceId, ReservationRequest reservationRequest) {
		final String reservationLockKey = "PERFORMANCE:" + performanceId;
		final RLock lock = redisson.getLock(reservationLockKey);

		try {
			if (lock.tryLock(5000, 4000, TimeUnit.MILLISECONDS)) {
				final ReservationService transactionAppliedReservationService = applicationContext.getBean(ReservationService.class);

				final Reservation reservation = transactionAppliedReservationService.startReserveTransaction(memberId, performanceId, reservationRequest);

				return reservation;
			} else {
				throw new NumbleReservationException(NumbleErrorCode.ANOTHER_RESERVATION_PROCESSING);
			}
		} catch (InterruptedException exception) {
			throw new NumbleReservationException("UNKNOWN Exception");
		} finally {
			if (lock.isLocked()) {
				lock.unlock();
			}
		}
	}

	@Transactional
	public Reservation startReserveTransaction(Long memberId, Long performanceId, ReservationRequest reservationRequest) {
		if (Objects.isNull(reservationRequest)) {
			throw new NumbleReservationException("empty reservation request");
		}

		final Performance performance = performanceRepository.findById(performanceId)
			.map(PerformanceEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException("not found performance id:" + performanceId));

		reservationValidationService.validateEligibleForReservation(performance, reservationRequest);
		performanceCapacityService.deleteCapacity(performanceId, reservationRequest.getRequestedReservationSeatCount());

		final Reservation reservation = createReservation(memberId, performance, reservationRequest);

		paymentGatewayApiClient.completePayment(PaymentCompleteRequest.builder()
			.memberId(memberId)
			.requestAmount(reservation.getTotalPrice())
			.paymentInfo(reservation.getPaymentInfo())
			.build());

		eventPublisher.publishEvent(PaymentCompleted.builder()
			.memberId(memberId)
			.requestAmount(reservation.getTotalPrice())
			.paymentInfo(reservation.getPaymentInfo())
			.build());

		return reservationRepository.save(ReservationEntity.fromDomain(reservation)).toDomain();
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void cancelPayment(PaymentCompleted paymentCompleted) {
		paymentGatewayApiClient.cancelPayment(PaymentCancelRequest.builder()
			.memberId(paymentCompleted.getMemberId())
			.paymentInfo(paymentCompleted.getPaymentInfo())
			.requestAmount(paymentCompleted.getRequestAmount())
			.build());
	}

	private Reservation createReservation(Long memberId, Performance performance, ReservationRequest reservationRequest) {
		final List<Long> seatIds = reservationRequest.getReservationSeatIds();
		final Venue venue = performance.getVenue();
		final Map<Long, VenueSeatType> venueSeatTypeBySeatId = venue.getVenueSeatTypeBySeatId();
		final BigDecimal totalPrice = reservationTotalPriceCalculatorService.calculateTotalPrice(performance, seatIds);

		final List<ReservationSeat> reservationSeats = seatIds.stream()
			.map(seatId -> ReservationSeat.create()
				.venueSeatId(seatId)
				.seatType(venueSeatTypeBySeatId.get(seatId))
				.build())
			.collect(Collectors.toList());

		return Reservation.create()
			.memberId(memberId)
			.performanceId(performance.getPerformanceId())
			.paymentInfo(reservationRequest.getPaymentInfo())
			.totalPrice(totalPrice)
			.vipPrice(performance.getVipPrice())
			.normalPrice(performance.getNormalPrice())
			.reservationSeats(reservationSeats)
			.build();
	}
}
