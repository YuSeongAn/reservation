package com.numble.reservation.persistence.spec;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.entity.venue.VenueEntity;

public class PerformanceEntitySpec {
	private PerformanceEntitySpec() {
	}

	public static PerformanceEntity of(VenueEntity venue, Long businessMemberId) {
		return PerformanceEntity.builder()
			.venue(venue)
			.businessMemberId(businessMemberId)
			.name("sing in seoul")
			.totalCapacity(1000)
			.remainCapacity(1000)
			.startAt(LocalDateTime.now().plusDays(2))
			.endAt(LocalDateTime.now().plusDays(2).plusHours(2))
			.normalPrice(BigDecimal.TEN)
			.vipPrice(BigDecimal.valueOf(15.0))
			.build();
	}
}
