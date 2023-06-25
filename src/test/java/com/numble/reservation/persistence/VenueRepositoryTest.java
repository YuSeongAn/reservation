package com.numble.reservation.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.entity.venue.VenueEntity;
import com.numble.reservation.persistence.entity.venue.VenueSeatEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.persistence.repository.venue.VenueRepository;
import com.numble.reservation.persistence.spec.BusinessMemberEntitySpec;
import com.numble.reservation.persistence.spec.VenueEntitySpec;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
public class VenueRepositoryTest {
	@Autowired
	private VenueRepository venueRepository;
	@Autowired
	private BusinessMemberRepository businessMemberRepository;

	@Test
	public void saveVenueEntity() {
		//given
		final BusinessMemberEntity businessMember = BusinessMemberEntitySpec.of();
		final Long businessMemberId = businessMemberRepository.save(businessMember).getBusinessMemberId();

		Assertions.assertNotNull(businessMemberId);

		final VenueEntity venue = VenueEntitySpec.of(businessMemberId);

		final VenueEntity savedVenue = venueRepository.save(venue);

		Assertions.assertNotNull(savedVenue);
		Assertions.assertNotNull(savedVenue.getVenueId());
		Assertions.assertEquals(venue.getName(), savedVenue.getName());
		Assertions.assertEquals(venue.getVenueType(), savedVenue.getVenueType());
		Assertions.assertEquals(venue.getRunningStartedAt(), savedVenue.getRunningStartedAt());
		Assertions.assertEquals(venue.getRunningEndedAt(), savedVenue.getRunningEndedAt());
		Assertions.assertEquals(venue.getVenueSeats().size(), savedVenue.getVenueSeats().size());

		for (int i = 0; i < savedVenue.getVenueSeats().size(); i++) {
			final VenueSeatEntity venueSeat = venue.getVenueSeats().get(i);
			final VenueSeatEntity savedVenueSeat = savedVenue.getVenueSeats().get(i);

			Assertions.assertNotNull(savedVenueSeat);
			Assertions.assertNotNull(savedVenueSeat.getSeatId());

			Assertions.assertEquals(venueSeat.getSeatNumber(), savedVenueSeat.getSeatNumber());
			Assertions.assertEquals(venueSeat.getSeatType(), savedVenueSeat.getSeatType());
		}
	}
}
