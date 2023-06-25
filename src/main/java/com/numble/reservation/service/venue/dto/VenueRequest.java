package com.numble.reservation.service.venue.dto;

import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.numble.reservation.code.VenueType;

import lombok.Data;

@Data
public class VenueRequest {
	@Size(min = 3, max = 128)
	private String name;
	@NotNull
	private VenueType venueType;
	@NotNull
	private LocalTime runningStartedAt;
	@NotNull
	private LocalTime runningEndedAt;
	@Valid
	@Size(min = 1)
	private List<VenueSeatRequest> venueSeats;
}
