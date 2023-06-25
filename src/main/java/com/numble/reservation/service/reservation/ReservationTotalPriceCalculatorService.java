package com.numble.reservation.service.reservation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.numble.reservation.code.VenueSeatType;
import com.numble.reservation.domain.Performance;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.domain.VenueSeat;

@Component
public class ReservationTotalPriceCalculatorService {

	public BigDecimal calculateTotalPrice(Performance performance, List<Long> seatIds) {
		if (CollectionUtils.isEmpty(seatIds)) {
			return BigDecimal.ZERO;
		}

		final Venue venue = performance.getVenue();
		final Map<Long, VenueSeatType> venueSeatTypeMap = venue.getVenueSeats()
			.stream()
			.collect(Collectors.toMap(VenueSeat::getSeatId, VenueSeat::getSeatType, (x1, x2) -> x1));

		final long totalNormalSeatCount = seatIds.stream()
			.map(venueSeatTypeMap::get)
			.filter(Objects::nonNull)
			.filter(VenueSeatType.NORMAL::equals)
			.count();

		final long totalVipSeatCount = seatIds.stream()
			.map(venueSeatTypeMap::get)
			.filter(Objects::nonNull)
			.filter(VenueSeatType.VIP::equals)
			.count();

		final BigDecimal normalTotalPrice = performance.getNormalPrice().multiply(BigDecimal.valueOf(totalNormalSeatCount));
		final BigDecimal vipTotalPrice = performance.getVipPrice().multiply(BigDecimal.valueOf(totalVipSeatCount));

		return normalTotalPrice.add(vipTotalPrice);
	}

}
