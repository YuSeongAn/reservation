package com.numble.reservation.persistence.repository.venue;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.reservation.persistence.entity.venue.VenueEntity;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> , VenueCustomRepository{
}
