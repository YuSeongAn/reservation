package com.numble.reservation.service.reservation;

import static com.numble.reservation.common.NumbleErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.numble.reservation.domain.Performance;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.service.reservation.dto.ReservationRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationValidationService {
	private static final Integer MAX_RESERVABLE_SEATS = 5;

	private final ReservationProvider reservationProvider;

	public void validateEligibleForReservation(Performance performance, ReservationRequest reservationRequest) {
		final List<Long> requestedSeatIds = reservationRequest.getReservationSeatIds();
		final int requestedReserveSeatCount = requestedSeatIds.size();

		if (MAX_RESERVABLE_SEATS < requestedReserveSeatCount) {
			throw new NumbleReservationException(RESERVATION_SEAT_EXCEED_LIMIT);
		}

		performance.checkReservable(requestedReserveSeatCount);

		if (!CollectionUtils.isEmpty(reservationProvider.getReservationSeatBy(requestedSeatIds))) {
			throw new NumbleReservationException(RESERVATION_SEAT_OCCUPIED);
		}
	}
}
