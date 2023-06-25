package com.numble.reservation.service.venue;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.code.BusinessMemberType;
import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.domain.VenueSeat;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.persistence.repository.venue.VenueRepository;
import com.numble.reservation.service.venue.dto.VenueDto;
import com.numble.reservation.service.venue.dto.VenueRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VenueRegisterService {
	private final BusinessMemberRepository businessMemberRepository;
	private final VenueRepository venueRepository;

	public Venue registerVenue(Long businessMemberId, VenueRequest venueRequest) {
		if (Objects.isNull(venueRequest)) {
			throw new NumbleReservationException("empty venue request");
		}

		final BusinessMember businessMember = businessMemberRepository.findById(businessMemberId)
			.map(BusinessMemberEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));

		if (BusinessMemberType.VENUE_ADMIN != businessMember.getBusinessMemberType()) {
			throw new NumbleReservationException(NumbleErrorCode.UNAUTHORIZED);
		}

		final Venue venue = createVenue(businessMemberId, venueRequest);

		return venueRepository.save(VenueEntity.fromDomain(venue)).toDomain();
	}

	private Venue createVenue(Long businessMemberId, VenueRequest venueRequest) {
		return Venue.create()
			.businessMemberId(businessMemberId)
			.name(venueRequest.getName())
			.venueType(venueRequest.getVenueType())
			.runningStartedAt(venueRequest.getRunningStartedAt())
			.runningEndedAt(venueRequest.getRunningEndedAt())
			.venueSeats(venueRequest.getVenueSeats()
				.stream()
				.map(venueSeatRequest -> VenueSeat.create()
					.seatNumber(venueSeatRequest.getSeatNumber())
					.seatType(venueSeatRequest.getSeatType())
					.build())
				.collect(Collectors.toList()))
			.build();
	}
}
