package com.numble.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.reservation.common.NumbleResponse;
import com.numble.reservation.domain.Reservation;
import com.numble.reservation.service.reservation.ReservationProvider;
import com.numble.reservation.service.reservation.dto.ReservationDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationProvider reservationProvider;

	@GetMapping("/{reservationId}")
	public NumbleResponse<ReservationDto> getReservation(@PathVariable Long reservationId) {
		final Reservation reservation = reservationProvider.getReservation(reservationId);

		return NumbleResponse.success(ReservationDto.fromDomain(reservation));
	}
}
