package com.numble.reservation.service.performance;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.code.BusinessMemberType;
import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.domain.Performance;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;
import com.numble.reservation.persistence.repository.venue.VenueRepository;
import com.numble.reservation.service.performance.dto.PerformanceDto;
import com.numble.reservation.service.performance.dto.PerformanceRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceRegisterService {
	private final PerformanceRepository performanceRepository;
	private final VenueRepository venueRepository;
	private final BusinessMemberRepository businessMemberRepository;

	public Performance registerPerformance(Long venueId, Long businessMemberId, PerformanceRequest performanceRequest) {
		if (Objects.isNull(performanceRequest)) {
			throw new NumbleReservationException("empty performance request");
		}

		final BusinessMember businessMember = businessMemberRepository.findById(businessMemberId)
			.map(BusinessMemberEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));

		if (BusinessMemberType.PERFORMANCE_ADMIN != businessMember.getBusinessMemberType()) {
			throw new NumbleReservationException(NumbleErrorCode.UNAUTHORIZED);
		}

		final VenueEntity venueEntity = venueRepository.findByVenueId(venueId)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));

		final Venue venue = venueEntity.toDomain();

		final Performance performance = createPerformance(venue, businessMemberId, performanceRequest);

		return performanceRepository.save(PerformanceEntity.fromDomain(venueEntity, performance)).toDomain();
	}

	private Performance createPerformance(Venue venue, Long businessMemberId, PerformanceRequest performanceRequest) {
		return Performance.create()
			.venue(venue)
			.businessMemberId(businessMemberId)
			.name(performanceRequest.getName())
			.totalCapacity(performanceRequest.getCapacity())
			.startAt(performanceRequest.getStartAt())
			.endAt(performanceRequest.getEndAt())
			.normalPrice(performanceRequest.getNormalPrice())
			.vipPrice(performanceRequest.getVipPrice())
			.build();
	}

}
