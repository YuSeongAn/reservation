package com.numble.reservation.service.performance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.Performance;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceCapacityService {
	private final PerformanceRepository performanceRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCapacity(Long performanceId, Integer requestedCapacity) {
		final PerformanceEntity performanceEntity = performanceRepository.findById(performanceId)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));

		final Performance performance = performanceEntity.toDomain();
		performance.decreaseCapacity(requestedCapacity);

		performanceEntity.write(performance.getRemainCapacity());
	}
}
