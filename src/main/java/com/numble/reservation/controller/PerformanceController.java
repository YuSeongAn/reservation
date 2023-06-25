package com.numble.reservation.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numble.reservation.common.NumbleResponse;
import com.numble.reservation.domain.Member;
import com.numble.reservation.domain.Performance;
import com.numble.reservation.domain.Reservation;
import com.numble.reservation.service.performance.PerformanceProvider;
import com.numble.reservation.service.performance.dto.PerformanceDto;
import com.numble.reservation.service.reservation.ReservationService;
import com.numble.reservation.service.reservation.dto.ReservationDto;
import com.numble.reservation.service.reservation.dto.ReservationRequest;
import com.numble.reservation.service.reservation.dto.ReservationSeatDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {
	private final PerformanceProvider performanceProvider;
	private final ReservationService reservationService;

	@PostMapping("/{performanceId}/reservations")
	public NumbleResponse<ReservationDto> reserve(Member member, @PathVariable Long performanceId, @RequestBody @Valid ReservationRequest reservationRequest) {
		final Reservation reservation = reservationService.reserve(member.getMemberId(), performanceId, reservationRequest);

		return NumbleResponse.success(ReservationDto.fromDomain(reservation));
	}

	@GetMapping("/seats")
	public NumbleResponse<List<ReservationSeatDto>> getReservedSeats() {
		//TODO implement

		//VenueSeat, ReservationSeat 두개를 말아서 PerformanceSeatDto 목록 던져주자.
		return null;
	}

	@GetMapping("/reservable")
	public NumbleResponse<Page<PerformanceDto>> getReservablePerformances(
		@RequestParam(required = false, defaultValue = "0") Integer page,
		@RequestParam(required = false, defaultValue = "20") Integer size) {

		final Page<Performance> pagedPerformances = performanceProvider.findReservablePerformancesOn(PageRequest.of(page, size));

		return NumbleResponse.success(getPagedPerformanceDtos(pagedPerformances, PageRequest.of(page, size)));
	}

	@GetMapping("/best")
	public NumbleResponse<Page<PerformanceDto>> getBestPerformances(
		@RequestParam(required = false, defaultValue = "0") Integer page,
		@RequestParam(required = false, defaultValue = "20") Integer size) {

		final Page<Performance> pagedPerformances = performanceProvider.findBestPerformances(PageRequest.of(page, size));

		return NumbleResponse.success(getPagedPerformanceDtos(pagedPerformances, PageRequest.of(page, size)));
	}

	@GetMapping("/cheapest")
	public NumbleResponse<Page<PerformanceDto>> getCheapestPerformances(
		@RequestParam(required = false, defaultValue = "0") Integer page,
		@RequestParam(required = false, defaultValue = "20") Integer size) {

		final Page<Performance> pagedPerformances = performanceProvider.findCheapestPerformances(PageRequest.of(page, size));

		return NumbleResponse.success(getPagedPerformanceDtos(pagedPerformances, PageRequest.of(page, size)));
	}

	private static Page<PerformanceDto> getPagedPerformanceDtos(Page<Performance> pagedPerformances, PageRequest pageRequest) {
		if (Objects.isNull(pagedPerformances) || CollectionUtils.isEmpty(pagedPerformances.getContent())) {
			return Page.empty();
		}

		return new PageImpl<>(
			pagedPerformances.getContent()
				.stream()
				.map(PerformanceDto::fromDomain)
				.collect(Collectors.toList()),
			pageRequest,
			pagedPerformances.getTotalElements()
		);
	}
}
