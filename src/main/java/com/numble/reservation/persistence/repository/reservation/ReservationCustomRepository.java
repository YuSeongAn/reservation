package com.numble.reservation.persistence.repository.reservation;

import java.util.List;
import java.util.Optional;

import com.numble.reservation.persistence.entity.reservation.ReservationEntity;
import com.numble.reservation.persistence.entity.reservation.ReservationSeatEntity;

public interface ReservationCustomRepository {
	List<ReservationSeatEntity> findAllReservationSeatsByPerformanceId(Long performanceId);

	List<ReservationSeatEntity> findByVenueSeatIds(List<Long> venueSeatIds);

	Optional<ReservationEntity> findByReservationId(Long reservationId);
}
