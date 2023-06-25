package com.numble.reservation.domain;

import com.numble.reservation.code.VenueSeatType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VenueSeat {
	private Long seatId;
	private String seatNumber;
	private VenueSeatType seatType;

	@Builder(builderMethodName = "builder")
	public VenueSeat(Long seatId, String seatNumber, VenueSeatType seatType) {
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this.seatType = seatType;
	}

	@Builder(builderMethodName = "create")
	public VenueSeat(String seatNumber, VenueSeatType seatType) {
		this.seatNumber = seatNumber;
		this.seatType = seatType;
	}

}
