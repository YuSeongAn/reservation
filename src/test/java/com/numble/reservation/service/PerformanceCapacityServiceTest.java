package com.numble.reservation.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.common.NumbleResponse;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;
import com.numble.reservation.persistence.repository.venue.VenueRepository;
import com.numble.reservation.persistence.spec.BusinessMemberEntitySpec;
import com.numble.reservation.persistence.spec.VenueEntitySpec;
import com.numble.reservation.service.performance.PerformanceCapacityService;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
public class PerformanceCapacityServiceTest {

	@Autowired
	private PerformanceRepository performanceRepository;
	@Autowired
	private VenueRepository venueRepository;
	@Autowired
	private BusinessMemberRepository businessMemberRepository;

	@Autowired
	private PerformanceCapacityService performanceCapacityService;

	@Test
	public void decreaseCapacity() {
		//given
		final BusinessMemberEntity businessMember = businessMemberRepository.save(BusinessMemberEntitySpec.of());
		final VenueEntity venue = venueRepository.save(VenueEntitySpec.of(businessMember.getBusinessMemberId()));

		final PerformanceEntity performanceEntity = PerformanceEntity.builder()
			.venue(venue)
			.businessMemberId(businessMember.getBusinessMemberId())
			.name("test")
			.totalCapacity(10)
			.remainCapacity(10)
			.startAt(LocalDateTime.now().plusDays(2))
			.endAt(LocalDateTime.now().plusDays(2).plusHours(2))
			.normalPrice(BigDecimal.ONE)
			.vipPrice(BigDecimal.TEN)
			.build();

		performanceRepository.save(performanceEntity);

		//when
		performanceCapacityService.deleteCapacity(performanceEntity.getPerformanceId(), 5);

		//then
		final PerformanceEntity updatedPerformanceEntity = performanceRepository.findById(performanceEntity.getPerformanceId()).get();
		Assertions.assertEquals(5, updatedPerformanceEntity.getRemainCapacity());
	}

	@Test
	public void decreaseCapacity_largerThan_remainCapacity_throwException() {
		//given
		final BusinessMemberEntity businessMember = businessMemberRepository.save(BusinessMemberEntitySpec.of());
		final VenueEntity venue = venueRepository.save(VenueEntitySpec.of(businessMember.getBusinessMemberId()));

		final PerformanceEntity performanceEntity = PerformanceEntity.builder()
			.venue(venue)
			.businessMemberId(businessMember.getBusinessMemberId())
			.name("test")
			.totalCapacity(10)
			.remainCapacity(10)
			.startAt(LocalDateTime.now().plusDays(2))
			.endAt(LocalDateTime.now().plusDays(2).plusHours(2))
			.normalPrice(BigDecimal.ONE)
			.vipPrice(BigDecimal.TEN)
			.build();

		performanceRepository.save(performanceEntity);

		//when
		Assertions.assertThrows(NumbleReservationException.class, () -> performanceCapacityService.deleteCapacity(performanceEntity.getPerformanceId(), 11));
	}
}
