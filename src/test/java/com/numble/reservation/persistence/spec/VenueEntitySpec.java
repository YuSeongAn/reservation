package com.numble.reservation.persistence.spec;

import java.time.LocalTime;
import java.util.List;

import com.numble.reservation.code.VenueSeatType;
import com.numble.reservation.code.VenueType;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.entity.venue.VenueSeatEntity;

public class VenueEntitySpec {
	private VenueEntitySpec() {
	}

	public static VenueEntity of(Long businessMemberId) {
		final VenueSeatEntity venueSeatEntity1 = VenueSeatEntity.builder()
			.seatNumber("A10")
			.seatType(VenueSeatType.NORMAL)
			.build();

		final VenueSeatEntity venueSeatEntity2 = VenueSeatEntity.builder()
			.seatNumber("A11")
			.seatType(VenueSeatType.VIP)
			.build();

		return VenueEntity.builder()
			.businessMemberId(businessMemberId)
			.name("seoul venue")
			.venueType(VenueType.FIXED_TYPE)
			.runningStartedAt(LocalTime.of(9, 0))
			.runningEndedAt(LocalTime.of(20, 0))
			.venueSeats(List.of(venueSeatEntity1, venueSeatEntity2))
			.build();
	}
}
