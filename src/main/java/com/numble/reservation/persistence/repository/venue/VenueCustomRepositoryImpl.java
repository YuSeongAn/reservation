package com.numble.reservation.persistence.repository.venue;

import static com.numble.reservation.persistence.entity.venue.QVenueEntity.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VenueCustomRepositoryImpl implements VenueCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<VenueEntity> findByVenueId(Long venueId) {
		final VenueEntity venue = queryFactory.selectFrom(venueEntity)
			.where(venueEntity.venueId.eq(venueId))
			.leftJoin(venueEntity.venueSeats)
			.fetchJoin()
			.fetchOne();

		return Optional.ofNullable(venue);
	}
}
