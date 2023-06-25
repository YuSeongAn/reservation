package com.numble.reservation.service.venue.dto;

import java.util.List;
import java.util.Objects;

import com.numble.reservation.code.VenueType;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.utils.DateTimeUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenueDto {
	private Long venueId;
	private Long businessMemberId;
	private String name;
	private VenueType venueType;
	private String runningStarted;
	private String runningEndedAt;
	private List<VenueSeatDto> venueSeats;
	private String createdAt;
	private Long createdBy;
	private String lastModifiedAt;
	private Long lastModifiedBy;

	public static VenueDto fromDomain(Venue venue) {
		if (Objects.isNull(venue)) {
			return null;
		}

		return VenueDto.builder()
			.venueId(venue.getVenueId())
			.businessMemberId(venue.getVenueId())
			.name(venue.getName())
			.venueType(venue.getVenueType())
			.runningStarted(DateTimeUtils.getFrontTime(venue.getRunningStartedAt()))
			.runningEndedAt(DateTimeUtils.getFrontTime(venue.getRunningEndedAt()))
			.venueSeats(VenueSeatDto.fromEntity(venue.getVenueSeats()))
			.createdAt(DateTimeUtils.getFrontDateTime(venue.getCreatedAt()))
			.createdBy(venue.getCreatedBy())
			.lastModifiedAt(DateTimeUtils.getFrontDateTime(venue.getLastModifiedAt()))
			.lastModifiedBy(venue.getLastModifiedBy())
			.build();
	}
}
