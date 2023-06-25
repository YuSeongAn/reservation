package com.numble.reservation.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.persistence.repository.performance.PerformanceRepository;
import com.numble.reservation.persistence.repository.venue.VenueRepository;
import com.numble.reservation.persistence.spec.BusinessMemberEntitySpec;
import com.numble.reservation.persistence.spec.PerformanceEntitySpec;
import com.numble.reservation.persistence.spec.VenueEntitySpec;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
public class PerformanceRepositoryTest {
	@Autowired
	private PerformanceRepository performanceRepository;

	@Autowired
	private BusinessMemberRepository businessMemberRepository;

	@Autowired
	private VenueRepository venueRepository;

	private VenueEntity venue;
	private Long businessMemberId;

	@BeforeEach
	public void setUp() {
		final BusinessMemberEntity businessMemberEntity = businessMemberRepository.save(BusinessMemberEntitySpec.of());
		businessMemberId = businessMemberEntity.getBusinessMemberId();
		venue = venueRepository.save(VenueEntitySpec.of(businessMemberEntity.getBusinessMemberId()));
	}

	@Test
	public void savePerformanceEntity() {
		final PerformanceEntity performance = PerformanceEntitySpec.of(venue, businessMemberId);

		final PerformanceEntity savedPerformance = performanceRepository.save(performance);

		Assertions.assertNotNull(savedPerformance);
		Assertions.assertNotNull(savedPerformance.getPerformanceId());
		Assertions.assertNotNull(savedPerformance.getVenue());

		Assertions.assertEquals(performance.getBusinessMemberId(), savedPerformance.getBusinessMemberId());
		Assertions.assertEquals(performance.getName(), savedPerformance.getName());
		Assertions.assertEquals(performance.getStartAt(), savedPerformance.getStartAt());
		Assertions.assertEquals(performance.getEndAt(), savedPerformance.getEndAt());
		Assertions.assertEquals(performance.getNormalPrice(), savedPerformance.getNormalPrice());
		Assertions.assertEquals(performance.getVipPrice(), savedPerformance.getVipPrice());
	}
}
