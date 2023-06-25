package com.numble.reservation.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Reservation {
	private Long reservationId;
	private Long memberId;
	private Long performanceId;
	private PaymentInfo paymentInfo;
	private BigDecimal totalPrice;
	private BigDecimal normalPrice;
	private BigDecimal vipPrice;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private List<ReservationSeat> reservationSeats;

	@Builder(builderMethodName = "builder")
	public Reservation(Long reservationId, Long memberId, Long performanceId, PaymentInfo paymentInfo, BigDecimal totalPrice, BigDecimal normalPrice,
		BigDecimal vipPrice, LocalDateTime createdAt, LocalDateTime lastModifiedAt, List<ReservationSeat> reservationSeats) {
		this.reservationId = reservationId;
		this.memberId = memberId;
		this.performanceId = performanceId;
		this.paymentInfo = paymentInfo;
		this.totalPrice = totalPrice;
		this.normalPrice = normalPrice;
		this.vipPrice = vipPrice;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
		this.reservationSeats = reservationSeats;
	}

	@Builder(builderMethodName = "create")
	public Reservation(Long memberId, Long performanceId, PaymentInfo paymentInfo, BigDecimal totalPrice, BigDecimal normalPrice, BigDecimal vipPrice, List<ReservationSeat> reservationSeats) {
		this.memberId = memberId;
		this.performanceId = performanceId;
		this.paymentInfo = paymentInfo;
		this.totalPrice = totalPrice;
		this.normalPrice = normalPrice;
		this.vipPrice = vipPrice;
		this.reservationSeats = reservationSeats;
	}
}
