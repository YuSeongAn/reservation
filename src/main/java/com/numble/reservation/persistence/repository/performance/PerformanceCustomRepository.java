package com.numble.reservation.persistence.repository.performance;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.numble.reservation.domain.ReservationSeat;
import com.numble.reservation.persistence.entity.performance.PerformanceEntity;

public interface PerformanceCustomRepository {
	Page<PerformanceEntity> findReservablePerformancesOn(LocalDateTime localDateTime, PageRequest pageRequest);

	Page<PerformanceEntity> findBestPerformances(PageRequest pageRequest);

	Page<PerformanceEntity> findCheapestPerformances(PageRequest pageRequest);
}
