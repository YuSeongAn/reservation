package com.numble.reservation.service.performance;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.numble.reservation.domain.Performance;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PerformanceProvider {
	private final PerformanceRepository performanceRepository;

	public Page<Performance> findReservablePerformancesOn(PageRequest pageRequest) {
		final Page<PerformanceEntity> pagedPerformances = performanceRepository.findReservablePerformancesOn(LocalDateTime.now(), pageRequest);

		return getPagedPerformance(pagedPerformances, pageRequest);
	}

	public Page<Performance> findBestPerformances(PageRequest pageRequest) {
		final Page<PerformanceEntity> pagedPerformances = performanceRepository.findBestPerformances(pageRequest);

		return getPagedPerformance(pagedPerformances, pageRequest);
	}

	public Page<Performance> findCheapestPerformances(PageRequest pageRequest){
		final Page<PerformanceEntity> pagedPerformances = performanceRepository.findCheapestPerformances(pageRequest);

		return getPagedPerformance(pagedPerformances, pageRequest);
	}

	private Page<Performance> getPagedPerformance(Page<PerformanceEntity> pagedPerformanceEntities, PageRequest pageRequest) {
		if (Objects.isNull(pagedPerformanceEntities) || CollectionUtils.isEmpty(pagedPerformanceEntities.getContent())) {
			return Page.empty();
		}

		return new PageImpl<>(
			pagedPerformanceEntities.getContent()
				.stream()
				.map(PerformanceEntity::toDomain)
				.collect(Collectors.toList()),
			pageRequest,
			pagedPerformanceEntities.getTotalElements()
		);
	}
}
