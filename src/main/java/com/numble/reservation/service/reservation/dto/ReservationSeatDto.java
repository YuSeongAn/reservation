package com.numble.reservation.service.reservation.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.numble.reservation.code.VenueSeatType;
import com.numble.reservation.domain.ReservationSeat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationSeatDto {
	private Long reservationSeatId;
	private Long venueSeatId;
	private VenueSeatType seatType;

	public static List<ReservationSeatDto> fromDomain(List<ReservationSeat> reservationSeats) {
		if (CollectionUtils.isEmpty(reservationSeats)) {
			return Collections.emptyList();
		}

		return reservationSeats.stream()
			.map(ReservationSeatDto::fromDomain)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	public static ReservationSeatDto fromDomain(ReservationSeat reservationSeat) {
		if (Objects.isNull(reservationSeat)) {
			return null;
		}

		return ReservationSeatDto.builder()
			.reservationSeatId(reservationSeat.getReservationSeatId())
			.venueSeatId(reservationSeat.getVenueSeatId())
			.seatType(reservationSeat.getSeatType())
			.build();
	}
}
