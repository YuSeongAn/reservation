package com.numble.reservation.service.reservation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.Reservation;
import com.numble.reservation.domain.ReservationSeat;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.reservation.ReservationEntity;
import com.numble.reservation.persistence.entity.reservation.ReservationSeatEntity;
import com.numble.reservation.persistence.repository.reservation.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationProvider {
	private final ReservationRepository reservationRepository;

	public List<ReservationSeat> getAllReservationSeat(Long performanceId) {
		return reservationRepository.findAllReservationSeatsByPerformanceId(performanceId)
			.stream()
			.map(ReservationSeatEntity::toDomain)
			.collect(Collectors.toList());
	}

	public List<ReservationSeat> getReservationSeatBy(List<Long> venueSeatIds) {
		return reservationRepository.findByVenueSeatIds(venueSeatIds)
			.stream()
			.map(ReservationSeatEntity::toDomain)
			.collect(Collectors.toList());
	}

	public Reservation getReservation(Long reservationId) {
		return reservationRepository.findByReservationId(reservationId)
			.map(ReservationEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));
	}
}
