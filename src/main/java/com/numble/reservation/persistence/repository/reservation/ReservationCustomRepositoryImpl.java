package com.numble.reservation.persistence.repository.reservation;

import static com.numble.reservation.persistence.entity.reservation.QReservationEntity.*;
import static com.numble.reservation.persistence.entity.reservation.QReservationSeatEntity.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.numble.reservation.persistence.entity.reservation.ReservationEntity;
import com.numble.reservation.persistence.entity.reservation.ReservationSeatEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<ReservationSeatEntity> findAllReservationSeatsByPerformanceId(Long performanceId) {
		return queryFactory.selectFrom(reservationSeatEntity)
			.join(reservationSeatEntity.reservation, reservationEntity)
			.where(reservationEntity.performanceId.eq(performanceId))
			.fetch();
	}

	@Override
	public List<ReservationSeatEntity> findByVenueSeatIds(List<Long> venueSeatIds) {
		if (CollectionUtils.isEmpty(venueSeatIds)) {
			return Collections.emptyList();
		}

		return queryFactory.selectFrom(reservationSeatEntity)
			.where(reservationSeatEntity.venueSeatId.in(venueSeatIds))
			.fetch();
	}

	@Override
	public Optional<ReservationEntity> findByReservationId(Long reservationId) {
		final ReservationEntity reservation = queryFactory.selectFrom(reservationEntity)
			.where(reservationEntity.reservationId.eq(reservationId))
			.leftJoin(reservationEntity.reservationSeats)
			.fetchJoin()
			.fetchOne();

		return Optional.ofNullable(reservation);
	}
}
