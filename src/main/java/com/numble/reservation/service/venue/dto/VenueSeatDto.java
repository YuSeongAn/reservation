package com.numble.reservation.service.venue.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.numble.reservation.code.VenueSeatType;
import com.numble.reservation.domain.VenueSeat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenueSeatDto {
	private Long seatId;
	private String seatNumber;
	private VenueSeatType seatType;

	public static VenueSeatDto fromEntity(VenueSeat venueSeat) {
		if (Objects.isNull(venueSeat)) {
			return null;
		}

		return VenueSeatDto.builder()
			.seatId(venueSeat.getSeatId())
			.seatNumber(venueSeat.getSeatNumber())
			.seatType(venueSeat.getSeatType())
			.build();
	}

	public static List<VenueSeatDto> fromEntity(List<VenueSeat> venueSeats) {
		if (CollectionUtils.isEmpty(venueSeats)) {
			return Collections.emptyList();
		}

		return venueSeats.stream()
			.map(VenueSeatDto::fromEntity)
			.collect(Collectors.toList());
	}
}
