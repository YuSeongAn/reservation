package com.numble.reservation.persistence.entity.venue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.numble.reservation.code.VenueType;
import com.numble.reservation.domain.Venue;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "venue")
@EqualsAndHashCode(of = "venueId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VenueEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long venueId;

	@Column
	private Long businessMemberId;

	@Column
	private String name;

	@Enumerated(EnumType.STRING)
	@Column
	private VenueType venueType;

	@Column
	private LocalTime runningStartedAt;

	@Column
	private LocalTime runningEndedAt;

	@OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<VenueSeatEntity> venueSeats = new ArrayList<>();

	@CreatedDate
	private LocalDateTime createdAt;

	@Column
	private Long createdBy;

	@LastModifiedDate
	private LocalDateTime lastModifiedAt;

	@Column
	private Long lastModifiedBy;

	@Builder
	public VenueEntity(Long businessMemberId, String name, VenueType venueType, LocalTime runningStartedAt,
		LocalTime runningEndedAt, List<VenueSeatEntity> venueSeats) {

		this.businessMemberId = businessMemberId;
		this.name = name;
		this.venueType = venueType;
		this.runningStartedAt = runningStartedAt;
		this.runningEndedAt = runningEndedAt;
		this.venueSeats = venueSeats;
		this.venueSeats.forEach(venueSeat -> venueSeat.apply(this));
	}

	@PrePersist
	public void beforePersist() {
		this.createdBy = businessMemberId;
		this.lastModifiedBy = businessMemberId;
	}

	@PreUpdate
	public void beforeUpdate() {
		this.lastModifiedBy = businessMemberId;
	}

	public Venue toDomain() {
		return Venue.builder()
			.venueId(venueId)
			.businessMemberId(businessMemberId)
			.name(name)
			.venueType(venueType)
			.runningStartedAt(runningStartedAt)
			.runningEndedAt(runningEndedAt)
			.venueSeats(venueSeats.stream()
				.map(VenueSeatEntity::toDomain)
				.collect(Collectors.toList()))
			.createdAt(createdAt)
			.createdBy(createdBy)
			.lastModifiedAt(lastModifiedAt)
			.lastModifiedBy(lastModifiedBy)
			.build();
	}

	public static VenueEntity fromDomain(Venue venue) {
		if (Objects.isNull(venue)) {
			return null;
		}

		return VenueEntity.builder()
			.businessMemberId(venue.getBusinessMemberId())
			.name(venue.getName())
			.venueType(venue.getVenueType())
			.runningStartedAt(venue.getRunningStartedAt())
			.runningEndedAt(venue.getRunningEndedAt())
			.venueSeats(VenueSeatEntity.fromDomain(venue.getVenueSeats()))
			.build();
	}
}
