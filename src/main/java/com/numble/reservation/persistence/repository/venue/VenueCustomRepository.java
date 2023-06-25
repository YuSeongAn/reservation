package com.numble.reservation.persistence.repository.venue;

import java.util.Optional;

import com.numble.reservation.persistence.entity.venue.VenueEntity;

public interface VenueCustomRepository {
	Optional<VenueEntity> findByVenueId(Long venueId);
}
