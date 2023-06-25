package com.numble.reservation.persistence.entity.performance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.numble.reservation.domain.Performance;
import com.numble.reservation.domain.Venue;
import com.numble.reservation.persistence.entity.venue.VenueEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "performance")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "performanceId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long performanceId;

	@JoinColumn(name = "venueId")
	@ManyToOne(fetch = FetchType.LAZY)
	private VenueEntity venue;

	@Column
	private Long businessMemberId;

	@Column
	private String name;

	@Column
	private Integer totalCapacity;

	@Column
	private Integer remainCapacity;

	@Column
	private LocalDateTime startAt;

	@Column
	private LocalDateTime endAt;

	@Column
	private BigDecimal normalPrice;

	@Column
	private BigDecimal vipPrice;

	@CreatedDate
	private LocalDateTime createdAt;

	@Column
	private Long createdBy;

	@LastModifiedDate
	private LocalDateTime lastModifiedAt;

	@Column
	private Long lastModifiedBy;

	@Builder
	public PerformanceEntity(VenueEntity venue, Long businessMemberId, String name, Integer totalCapacity, Integer remainCapacity,
		LocalDateTime startAt, LocalDateTime endAt, BigDecimal normalPrice, BigDecimal vipPrice) {

		this.venue = venue;
		this.businessMemberId = businessMemberId;
		this.name = name;
		this.totalCapacity = totalCapacity;
		this.remainCapacity = remainCapacity;
		this.startAt = startAt;
		this.endAt = endAt;
		this.normalPrice = normalPrice;
		this.vipPrice = vipPrice;
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

	public Performance toDomain() {
		return Performance.builder()
			.performanceId(performanceId)
			.venue(venue.toDomain())
			.businessMemberId(businessMemberId)
			.name(name)
			.totalCapacity(totalCapacity)
			.remainCapacity(remainCapacity)
			.startAt(startAt)
			.endAt(endAt)
			.normalPrice(normalPrice)
			.vipPrice(vipPrice)
			.createdAt(createdAt)
			.createdBy(createdBy)
			.lastModifiedAt(lastModifiedAt)
			.lastModifiedBy(lastModifiedBy)
			.build();
	}

	public static PerformanceEntity fromDomain(VenueEntity venue ,Performance performance) {
		if (Objects.isNull(performance)) {
			return null;
		}

		return PerformanceEntity.builder()
			.venue(venue)
			.businessMemberId(performance.getBusinessMemberId())
			.name(performance.getName())
			.totalCapacity(performance.getTotalCapacity())
			.remainCapacity(performance.getRemainCapacity())
			.startAt(performance.getStartAt())
			.endAt(performance.getEndAt())
			.normalPrice(performance.getNormalPrice())
			.vipPrice(performance.getVipPrice())
			.build();
	}

	public void write(Integer remainCapacity) {
		this.remainCapacity = remainCapacity;
	}
}
