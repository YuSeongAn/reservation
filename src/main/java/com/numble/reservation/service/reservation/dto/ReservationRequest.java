package com.numble.reservation.service.reservation.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.numble.reservation.domain.PaymentInfo;

import lombok.Data;

@Data
public class ReservationRequest {
	@Valid
	@NotEmpty
	private List<ReservationSeatRequest> reservationSeats;

	@NotNull
	private PaymentInfo paymentInfo;

	public List<Long> getReservationSeatIds() {
		return Optional.ofNullable(reservationSeats)
			.map(nonNullReservationSeats -> nonNullReservationSeats.stream()
				.map(ReservationSeatRequest::getSeatId)
				.collect(Collectors.toList()))
			.orElseGet(Collections::emptyList);
	}

	public int getRequestedReservationSeatCount() {
		return Optional.ofNullable(reservationSeats)
			.map(List::size)
			.orElse(0);
	}
}
