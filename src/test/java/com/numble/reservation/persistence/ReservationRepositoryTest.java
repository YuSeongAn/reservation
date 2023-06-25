package com.numble.reservation.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.code.PaymentMethodType;
import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.entity.common.PaymentInfoVo;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.entity.reservation.ReservationEntity;
import com.numble.reservation.persistence.entity.reservation.ReservationSeatEntity;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.entity.venue.VenueSeatEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.persistence.repository.member.MemberRepository;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;
import com.numble.reservation.persistence.repository.reservation.ReservationRepository;
import com.numble.reservation.persistence.repository.venue.VenueRepository;
import com.numble.reservation.persistence.spec.BusinessMemberEntitySpec;
import com.numble.reservation.persistence.spec.MemberEntitySpec;
import com.numble.reservation.persistence.spec.PerformanceEntitySpec;
import com.numble.reservation.persistence.spec.VenueEntitySpec;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
public class ReservationRepositoryTest {
	@Autowired
	private PerformanceRepository performanceRepository;

	@Autowired
	private BusinessMemberRepository businessMemberRepository;

	@Autowired
	private VenueRepository venueRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	private PerformanceEntity performance;
	private Long memberId;

	private VenueEntity venue;

	@BeforeEach
	public void setUp() {
		final BusinessMemberEntity businessMember = businessMemberRepository.save(BusinessMemberEntitySpec.of());
		venue = venueRepository.save(VenueEntitySpec.of(businessMember.getBusinessMemberId()));
		performance = performanceRepository.save(PerformanceEntitySpec.of(venue, businessMember.getBusinessMemberId()));
		memberId = memberRepository.save(MemberEntitySpec.of()).getMemberId();
	}

	@Test
	public void saveReservationEntity() {
		final PaymentInfoVo paymentInfo = PaymentInfoVo.builder()
			.paymentMethodType(PaymentMethodType.CREDIT_CARD)
			.cardNumber("1111-1111-1111-1111")
			.cardExpiredAt(LocalDateTime.now().plusYears(3))
			.cardCVC("111")
			.build();

		final List<ReservationSeatEntity> reservationSeats = venue.getVenueSeats()
			.stream()
			.map(venueSeat -> ReservationSeatEntity.builder()
				.venueSeatId(venueSeat.getSeatId())
				.seatType(venueSeat.getSeatType())
				.build())
			.collect(Collectors.toList());

		final ReservationEntity reservation = ReservationEntity.builder()
			.memberId(memberId)
			.performanceId(performance.getPerformanceId())
			.paymentInfoVo(paymentInfo)
			.totalPrice(BigDecimal.TEN)
			.normalPrice(performance.getNormalPrice())
			.vipPrice(performance.getVipPrice())
			.reservationSeats(reservationSeats)
			.build();

		final ReservationEntity savedReservation = reservationRepository.save(reservation);

		Assertions.assertNotNull(savedReservation);
		Assertions.assertNotNull(savedReservation.getReservationId());

		Assertions.assertEquals(reservation.getPaymentInfoVo(), savedReservation.getPaymentInfoVo());
		Assertions.assertEquals(reservation.getTotalPrice(), savedReservation.getTotalPrice());
		Assertions.assertEquals(reservation.getNormalPrice(), savedReservation.getNormalPrice());
		Assertions.assertEquals(reservation.getVipPrice(), savedReservation.getVipPrice());
		Assertions.assertEquals(reservation.getReservationSeats().size(), savedReservation.getReservationSeats().size());

		final List<ReservationSeatEntity> savedReservationSeats = savedReservation.getReservationSeats();

		for (int i = 0; i < savedReservationSeats.size(); i++) {
			final ReservationSeatEntity savedReservationSeat = savedReservationSeats.get(i);
			final VenueSeatEntity venueSeat = venue.getVenueSeats().get(i);

			Assertions.assertNotNull(savedReservationSeats);
			Assertions.assertNotNull(savedReservationSeat.getReservation());
			Assertions.assertEquals(venueSeat.getSeatId(), savedReservationSeat.getVenueSeatId());
			Assertions.assertEquals(venueSeat.getSeatType(), savedReservationSeat.getSeatType());
		}
	}

	@Test
	public void findAllReservationSeatsByPerformanceId() {
		final List<ReservationSeatEntity> reservationSeats = reservationRepository.findAllReservationSeatsByPerformanceId(1L);

		Assertions.assertEquals(3, reservationSeats.size());
	}
}
