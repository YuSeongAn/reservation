package com.numble.reservation.service.performance.dto;

import java.math.BigDecimal;
import java.util.Objects;

import com.numble.reservation.domain.Performance;
import com.numble.reservation.service.venue.dto.VenueDto;
import com.numble.reservation.utils.DateTimeUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PerformanceDto {
	private Long performanceId;
	private VenueDto venue;
	private Long businessMemberId;
	private String name;
	private Integer totalCapacity;
	private Integer remainCapacity;
	private String startAt;
	private String endAt;
	private BigDecimal normalPrice;
	private BigDecimal vipPrice;
	private String createdAt;
	private Long createdBy;
	private String lastModifiedAt;
	private Long lastModifiedBy;

	public static PerformanceDto fromDomain(Performance performance) {
		if (Objects.isNull(performance)) {
			return null;
		}

		return PerformanceDto.builder()
			.performanceId(performance.getPerformanceId())
			.venue(VenueDto.fromDomain(performance.getVenue()))
			.businessMemberId(performance.getBusinessMemberId())
			.name(performance.getName())
			.totalCapacity(performance.getTotalCapacity())
			.remainCapacity(performance.getRemainCapacity())
			.startAt(DateTimeUtils.getFrontDateTime(performance.getStartAt()))
			.endAt(DateTimeUtils.getFrontDateTime(performance.getEndAt()))
			.normalPrice(performance.getNormalPrice())
			.vipPrice(performance.getVipPrice())
			.createdAt(DateTimeUtils.getFrontDateTime(performance.getCreatedAt()))
			.createdBy(performance.getCreatedBy())
			.lastModifiedAt(DateTimeUtils.getFrontDateTime(performance.getLastModifiedAt()))
			.lastModifiedBy(performance.getLastModifiedBy())
			.build();
	}
}
