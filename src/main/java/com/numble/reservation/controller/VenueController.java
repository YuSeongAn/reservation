package com.numble.reservation.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.reservation.common.NumbleResponse;
import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.domain.Performance;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.service.performance.PerformanceRegisterService;
import com.numble.reservation.service.performance.dto.PerformanceDto;
import com.numble.reservation.service.performance.dto.PerformanceRequest;
import com.numble.reservation.service.venue.VenueProvider;
import com.numble.reservation.service.venue.VenueRegisterService;
import com.numble.reservation.service.venue.dto.VenueDto;
import com.numble.reservation.service.venue.dto.VenueRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {
	private final VenueProvider venueProvider;
	private final PerformanceRegisterService performanceRegisterService;
	private final VenueRegisterService venueRegisterService;

	@GetMapping("/{venueId}")
	public NumbleResponse<VenueDto> getVenue(@PathVariable("venueId") Long venueId) {
		final Venue venue = venueProvider.getVenue(venueId);

		return NumbleResponse.success(VenueDto.fromDomain(venue));
	}

	@PostMapping
	public NumbleResponse<VenueDto> registerVenue(BusinessMember businessMember, @RequestBody @Valid VenueRequest venueRequest) {
		final Venue venue = venueRegisterService.registerVenue(businessMember.getBusinessMemberId(), venueRequest);

		return NumbleResponse.success(VenueDto.fromDomain(venue));
	}

	@PostMapping("/{venueId}/performances")
	public NumbleResponse<PerformanceDto> registerPerformance(BusinessMember businessMember, @PathVariable Long venueId, @RequestBody @Valid PerformanceRequest performanceRequest) {
		final Performance performance = performanceRegisterService.registerPerformance(venueId, businessMember.getBusinessMemberId(), performanceRequest);

		return NumbleResponse.success(PerformanceDto.fromDomain(performance));
	}
}
