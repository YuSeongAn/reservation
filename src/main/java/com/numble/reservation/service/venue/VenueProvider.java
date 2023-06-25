package com.numble.reservation.service.venue;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.repository.venue.VenueRepository;
import com.numble.reservation.service.venue.dto.VenueDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueProvider {
	private final VenueRepository venueRepository;

	@Transactional(readOnly = true)
	public Venue getVenue(Long venueId) {
		return venueRepository.findByVenueId(venueId)
			.map(VenueEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));
	}
}
