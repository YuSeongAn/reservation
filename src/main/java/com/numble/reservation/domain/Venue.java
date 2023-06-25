package com.numble.reservation.domain;

import static com.numble.reservation.common.NumbleErrorCode.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.numble.reservation.code.VenueSeatType;
import com.numble.reservation.code.VenueType;
import com.numble.reservation.exception.NumbleReservationException;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Venue {
	private Long venueId;
	private Long businessMemberId;
	private String name;
	private VenueType venueType;
	private LocalTime runningStartedAt;
	private LocalTime runningEndedAt;
	private List<VenueSeat> venueSeats;
	private LocalDateTime createdAt;
	private Long createdBy;
	private LocalDateTime lastModifiedAt;
	private Long lastModifiedBy;

	@Builder(builderMethodName = "builder")
	public Venue(Long venueId, Long businessMemberId, String name, VenueType venueType, LocalTime runningStartedAt, LocalTime runningEndedAt,
		List<VenueSeat> venueSeats, LocalDateTime createdAt, Long createdBy, LocalDateTime lastModifiedAt, Long lastModifiedBy) {

		this.venueId = venueId;
		this.businessMemberId = businessMemberId;
		this.name = name;
		this.venueType = venueType;
		this.runningStartedAt = runningStartedAt;
		this.runningEndedAt = runningEndedAt;
		this.venueSeats = venueSeats;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.lastModifiedAt = lastModifiedAt;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Builder(builderMethodName = "create")
	public Venue(Long businessMemberId, String name, VenueType venueType, LocalTime runningStartedAt, LocalTime runningEndedAt, List<VenueSeat> venueSeats) {
		this.businessMemberId = businessMemberId;
		this.name = name;
		this.venueType = venueType;
		this.runningStartedAt = runningStartedAt;
		this.runningEndedAt = runningEndedAt;
		this.venueSeats = venueSeats;

		checkValidRunningRange(runningStartedAt, runningEndedAt);
		checkValidSeats(venueSeats);
	}

	public Integer getCapacity() {
		return venueSeats.size();
	}

	public Map<Long, VenueSeatType> getVenueSeatTypeBySeatId() {
		if (CollectionUtils.isEmpty(venueSeats)) {
			return Collections.emptyMap();
		}

		return venueSeats.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toMap(VenueSeat::getSeatId, VenueSeat::getSeatType, (x1, x2) -> x1));
	}

	private void checkValidRunningRange(LocalTime runningStartedAt, LocalTime runningEndedAt) {
		if (runningStartedAt.isBefore(runningEndedAt)) {
			throw new NumbleReservationException(INVALID_VENUE_TIME_RANGE);
		}
	}

	private void checkValidSeats(List<VenueSeat> venueSeats) {
		final Map<String, List<VenueSeat>> venueSeatsBySeatNumber = venueSeats.stream()
			.collect(Collectors.groupingBy(VenueSeat::getSeatNumber));

		final boolean isExistDuplicateSeatNumberSeat = venueSeatsBySeatNumber.values()
			.stream()
			.anyMatch(venueSeatsWithSameSeatNumber -> venueSeatsWithSameSeatNumber.size() >= 2);

		if (isExistDuplicateSeatNumberSeat) {
			throw new NumbleReservationException(DUPLICATE_VENUE_SEAT);
		}
	}
}
