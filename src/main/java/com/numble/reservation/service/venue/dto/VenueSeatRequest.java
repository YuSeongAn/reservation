package com.numble.reservation.service.venue.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.numble.reservation.code.VenueSeatType;

import lombok.Data;

@Data
public class VenueSeatRequest {
	@Size(min = 1, max = 26)
	private String seatNumber;
	@NotNull
	private VenueSeatType seatType;
}
