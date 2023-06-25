package com.numble.reservation.persistence.repository.performance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.reservation.persistence.entity.performance.PerformanceEntity;

public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long>, PerformanceCustomRepository {
}
