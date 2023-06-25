package com.numble.reservation.domain;

import static com.numble.reservation.common.NumbleErrorCode.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.numble.reservation.exception.NumbleReservationException;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Performance {
	private Long performanceId;
	private Venue venue;
	private Long businessMemberId;
	private String name;
	private Integer totalCapacity;
	private Integer remainCapacity;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private BigDecimal normalPrice;
	private BigDecimal vipPrice;
	private LocalDateTime createdAt;
	private Long createdBy;
	private LocalDateTime lastModifiedAt;
	private Long lastModifiedBy;

	@Builder(builderClassName = "read", builderMethodName = "builder")
	public Performance(Long performanceId, Venue venue, Long businessMemberId, String name, Integer totalCapacity, Integer remainCapacity, LocalDateTime startAt,
		LocalDateTime endAt, BigDecimal normalPrice, BigDecimal vipPrice, LocalDateTime createdAt, Long createdBy, LocalDateTime lastModifiedAt, Long lastModifiedBy) {

		this.performanceId = performanceId;
		this.venue = venue;
		this.businessMemberId = businessMemberId;
		this.name = name;
		this.totalCapacity = totalCapacity;
		this.remainCapacity = remainCapacity;
		this.startAt = startAt;
		this.endAt = endAt;
		this.normalPrice = normalPrice;
		this.vipPrice = vipPrice;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.lastModifiedAt = lastModifiedAt;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Builder(builderClassName = "write", builderMethodName = "create")
	public Performance(Venue venue, Long businessMemberId, String name, Integer totalCapacity, LocalDateTime startAt, LocalDateTime endAt,
		BigDecimal normalPrice, BigDecimal vipPrice) {

		this.venue = venue;
		this.businessMemberId = businessMemberId;
		this.name = name;
		this.totalCapacity = totalCapacity;
		this.remainCapacity = totalCapacity;
		this.startAt = startAt;
		this.endAt = endAt;
		this.normalPrice = normalPrice;
		this.vipPrice = vipPrice;

		checkCapacityIsValid();
		checkPriceIsValid();
		checkPerformanceTimeIsValid();
	}

	public void checkReservable(int numberOfRequiredSeats) {
		final LocalDateTime now = LocalDateTime.now();

		if (now.isAfter(startAt)) {
			throw new NumbleReservationException(PERFORMANCE_STARTED);
		}

		if (remainCapacity < numberOfRequiredSeats) {
			throw new NumbleReservationException(PERFORMANCE_CAPACITY_OUT_OF_STOCK);
		}
	}

	public void decreaseCapacity(int requestedCapacity) {
		if (remainCapacity < requestedCapacity) {
			throw new NumbleReservationException(PERFORMANCE_CAPACITY_OUT_OF_STOCK);
		}

		remainCapacity -= requestedCapacity;
	}

	private void checkCapacityIsValid() {
		if (venue.getCapacity() < totalCapacity) {
			throw new NumbleReservationException(PERFORMANCE_CAPACITY_EXCEEDED);
		}
	}

	private void checkPriceIsValid() {
		if (vipPrice.compareTo(normalPrice) < 0) {
			throw new NumbleReservationException(INVALID_PERFORMANCE_PRICE);
		}
	}

	private void checkPerformanceTimeIsValid() {
		final LocalDateTime now = LocalDateTime.now();

		if (startAt.isAfter(endAt)) {
			throw new NumbleReservationException(INVALID_PERFORMANCE_TIME_RANGE);
		}

		if (now.isAfter(startAt)) {
			throw new NumbleReservationException(INVALID_PERFORMANCE_START_TIME_RANGE);
		}
	}
}
