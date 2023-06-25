package com.numble.reservation.persistence.entity.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.numble.reservation.domain.Reservation;
import com.numble.reservation.persistence.entity.common.PaymentInfoVo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reservation")
@EqualsAndHashCode(of = "reservationId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reservationId;

	@Column
	private Long memberId;

	@Column
	private Long performanceId;

	@Embedded
	private PaymentInfoVo paymentInfoVo;

	@Column
	private BigDecimal totalPrice;

	@Column
	private BigDecimal normalPrice;

	@Column
	private BigDecimal vipPrice;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime lastModifiedAt;

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ReservationSeatEntity> reservationSeats;

	@Builder
	public ReservationEntity(Long memberId, Long performanceId, PaymentInfoVo paymentInfoVo, BigDecimal totalPrice,
		BigDecimal normalPrice, BigDecimal vipPrice, List<ReservationSeatEntity> reservationSeats) {

		this.memberId = memberId;
		this.performanceId = performanceId;
		this.paymentInfoVo = paymentInfoVo;
		this.totalPrice = totalPrice;
		this.normalPrice = normalPrice;
		this.vipPrice = vipPrice;
		this.reservationSeats = reservationSeats;
		this.reservationSeats.forEach(reservationSeat -> reservationSeat.apply(this));
	}

	public Reservation toDomain() {
		return Reservation.builder()
			.reservationId(reservationId)
			.memberId(memberId)
			.performanceId(performanceId)
			.paymentInfo(paymentInfoVo.toDomain())
			.totalPrice(totalPrice)
			.normalPrice(normalPrice)
			.vipPrice(vipPrice)
			.createdAt(createdAt)
			.lastModifiedAt(lastModifiedAt)
			.reservationSeats(reservationSeats.stream()
				.map(ReservationSeatEntity::toDomain)
				.collect(Collectors.toList()))
			.build();
	}

	public static ReservationEntity fromDomain(Reservation reservation) {
		return ReservationEntity.builder()
			.memberId(reservation.getMemberId())
			.performanceId(reservation.getPerformanceId())
			.paymentInfoVo(PaymentInfoVo.fromDomain(reservation.getPaymentInfo()))
			.totalPrice(reservation.getTotalPrice())
			.normalPrice(reservation.getNormalPrice())
			.vipPrice(reservation.getVipPrice())
			.reservationSeats(ReservationSeatEntity.fromDomain(reservation.getReservationSeats()))
			.build();
	}
}
