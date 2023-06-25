package com.numble.reservation.persistence.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.reservation.persistence.entity.reservation.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>, ReservationCustomRepository {
}
