package com.numble.reservation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.code.PaymentMethodType;
import com.numble.reservation.domain.PaymentInfo;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.entity.reservation.ReservationSeatEntity;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;
import com.numble.reservation.persistence.repository.reservation.ReservationRepository;
import com.numble.reservation.service.reservation.ReservationService;
import com.numble.reservation.service.reservation.dto.ReservationRequest;
import com.numble.reservation.service.reservation.dto.ReservationSeatRequest;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:data.sql"})
public class ReservationServiceTest {
	@Autowired
	private PerformanceRepository performanceRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ReservationService reservationService;

	@Test
	public void reserveTest() throws InterruptedException {
		//given
		final int countOfThread = 6;
		final Long performanceId = 2L;

		final CountDownLatch countDownLatch = new CountDownLatch(countOfThread);

		//when
		IntStream.range(1, countOfThread + 1)
			.mapToObj(index -> {
				final PaymentInfo paymentInfo = PaymentInfo.builder()
					.cardCVC("111")
					.cardExpiredAt(LocalDateTime.now().plusYears(2))
					.paymentMethodType(PaymentMethodType.CREDIT_CARD)
					.cardNumber("1111-1111-1111-1111")
					.build();

				final ReservationSeatRequest reservationSeatRequest = new ReservationSeatRequest();
				reservationSeatRequest.setSeatId((long) index);

				final ReservationRequest reservationRequest = new ReservationRequest();
				reservationRequest.setReservationSeats(List.of(reservationSeatRequest));
				reservationRequest.setPaymentInfo(paymentInfo);

				return new VirtualMember(1L, performanceId, reservationRequest, countDownLatch, reservationService);
			})
			.map(Thread::new)
			.forEach(Thread::start);

		countDownLatch.await();

		//then
		final PerformanceEntity resultPerformance = performanceRepository.findById(performanceId).get();
		final List<ReservationSeatEntity> reservationSeats = reservationRepository.findAllReservationSeatsByPerformanceId(performanceId);

		Assertions.assertTrue(resultPerformance.getRemainCapacity() == 0);
		Assertions.assertEquals(30, reservationSeats.size());

		final Map<Long, List<ReservationSeatEntity>> reservationSeatEntityMap = reservationSeats.stream()
			.collect(Collectors.groupingBy(ReservationSeatEntity::getVenueSeatId));

		reservationSeatEntityMap.values()
			.forEach(reservationSeatEntities -> {
				Assertions.assertEquals(1, reservationSeatEntities.size());
			});
	}

	private static class VirtualMember implements Runnable {
		private Long memberId;
		private Long performanceId;
		private ReservationRequest reservationRequest;
		private CountDownLatch countDownLatch;
		private ReservationService reservationService;

		public VirtualMember(Long memberId, Long performanceId, ReservationRequest reservationRequest, CountDownLatch countDownLatch, ReservationService reservationService) {
			this.memberId = memberId;
			this.performanceId = performanceId;
			this.reservationRequest = reservationRequest;
			this.countDownLatch = countDownLatch;
			this.reservationService = reservationService;
		}

		@Override
		public void run() {
			try {
				reservationService.reserve(memberId, performanceId, reservationRequest);
			} finally {
				countDownLatch.countDown();
			}
		}
	}
}
