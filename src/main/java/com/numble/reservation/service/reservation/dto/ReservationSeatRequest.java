package com.numble.reservation.service.reservation.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReservationSeatRequest {
	@NotNull
	private Long seatId;
}
