package com.numble.reservation.domain;

import com.numble.reservation.code.VenueSeatType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationSeat {
	private Long reservationSeatId;
	private Long venueSeatId;
	private VenueSeatType seatType;

	@Builder(builderMethodName = "builder")
	public ReservationSeat(Long reservationSeatId, Long venueSeatId, VenueSeatType seatType) {
		this.reservationSeatId = reservationSeatId;
		this.venueSeatId = venueSeatId;
		this.seatType = seatType;
	}

	@Builder(builderMethodName = "create")
	public ReservationSeat(Long venueSeatId, VenueSeatType seatType) {
		this.venueSeatId = venueSeatId;
		this.seatType = seatType;
	}
}
