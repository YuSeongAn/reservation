package com.numble.reservation.persistence.repository.performance;

import static com.numble.reservation.persistence.entity.performance.QPerformanceEntity.*;
import static com.numble.reservation.persistence.entity.reservation.QReservationEntity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerformanceCustomRepositoryImpl implements PerformanceCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<PerformanceEntity> findReservablePerformancesOn(LocalDateTime localDateTime, PageRequest pageRequest) {
		final List<PerformanceEntity> performances = queryFactory.selectFrom(performanceEntity)
			.where(performanceEntity.startAt.after(localDateTime))
			.orderBy(performanceEntity.createdAt.desc())
			.offset(pageRequest.getOffset())
			.limit(pageRequest.getPageSize())
			.fetch();

		final Long totalElements = Optional.ofNullable(queryFactory.select(performanceEntity.count())
				.from(performanceEntity)
				.fetchOne())
			.orElse(0L);

		return new PageImpl<>(performances, pageRequest, totalElements);
	}

	@Override
	public Page<PerformanceEntity> findBestPerformances(PageRequest pageRequest) {
		final List<Long> bestReservedPerformanceIds = queryFactory.select(reservationEntity.performanceId)
			.from(reservationEntity)
			.groupBy(reservationEntity.performanceId)
			.orderBy(reservationEntity.performanceId.count().desc())
			.offset(pageRequest.getOffset())
			.limit(pageRequest.getPageSize())
			.fetch();

		final List<PerformanceEntity> notOrderedBestReservedPerformances = queryFactory.selectFrom(performanceEntity)
			.where(performanceEntity.performanceId.in(bestReservedPerformanceIds))
			.fetch();

		final Map<Long, PerformanceEntity> bestReservedPerformanceMap = notOrderedBestReservedPerformances.stream()
			.collect(Collectors.toMap(PerformanceEntity::getPerformanceId, Function.identity()));

		final List<PerformanceEntity> bestReservedPerformances = bestReservedPerformanceIds.stream()
			.map(bestReservedPerformanceMap::get)
			.collect(Collectors.toList());

		final Long totalElements = Optional.ofNullable(queryFactory.select(performanceEntity.count())
				.from(performanceEntity)
				.fetchOne())
			.orElse(0L);

		return new PageImpl<>(bestReservedPerformances, pageRequest, totalElements);
	}

	@Override
	public Page<PerformanceEntity> findCheapestPerformances(PageRequest pageRequest) {
		if (Objects.isNull(pageRequest)) {
			return Page.empty();
		}

		final List<PerformanceEntity> performances = queryFactory.selectFrom(performanceEntity)
			.orderBy(performanceEntity.normalPrice.asc())
			.offset(pageRequest.getOffset())
			.limit(pageRequest.getPageSize())
			.fetch();

		final Long totalElements = Optional.ofNullable(queryFactory.select(performanceEntity.count())
				.from(performanceEntity)
				.fetchOne())
			.orElse(0L);

		return new PageImpl<>(performances, pageRequest, totalElements);
	}
}
