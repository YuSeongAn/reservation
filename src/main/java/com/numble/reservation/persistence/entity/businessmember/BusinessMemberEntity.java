package com.numble.reservation.persistence.entity.businessmember;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.numble.reservation.code.BusinessMemberType;
import com.numble.reservation.domain.BusinessMember;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "business_member")
@EqualsAndHashCode(of = "businessMemberId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessMemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long businessMemberId;

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String businessLicense;

	@Column
	@Enumerated(EnumType.STRING)
	private BusinessMemberType businessMemberType;

	@Column
	@CreatedDate
	private LocalDateTime createdAt;

	@Column
	@LastModifiedDate
	private LocalDateTime lastModifiedAt;

	@Builder
	public BusinessMemberEntity(String email, String password, String businessLicense, BusinessMemberType businessMemberType) {
		this.email = email;
		this.password = password;
		this.businessLicense = businessLicense;
		this.businessMemberType = businessMemberType;
	}

	public BusinessMember toDomain() {
		return BusinessMember.builder()
			.businessMemberId(businessMemberId)
			.email(email)
			.password(password)
			.businessLicense(businessLicense)
			.businessMemberType(businessMemberType)
			.createdAt(createdAt)
			.lastModifiedAt(lastModifiedAt)
			.build();
	}

	public static BusinessMemberEntity fromDomain(BusinessMember businessMember) {
		if (Objects.isNull(businessMember)) {
			return null;
		}

		return BusinessMemberEntity.builder()
			.email(businessMember.getEmail())
			.password(businessMember.getPassword())
			.businessLicense(businessMember.getBusinessLicense())
			.businessMemberType(businessMember.getBusinessMemberType())
			.build();
	}
}
