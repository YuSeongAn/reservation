package com.numble.reservation.domain;

import java.time.LocalDateTime;

import com.numble.reservation.code.BusinessMemberType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BusinessMember {
	private Long businessMemberId;
	private String email;
	private String password;
	private String businessLicense;
	private BusinessMemberType businessMemberType;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

	@Builder(builderMethodName = "builder")
	public BusinessMember(Long businessMemberId, String email, String password, String businessLicense, BusinessMemberType businessMemberType,
		LocalDateTime createdAt, LocalDateTime lastModifiedAt) {

		this.businessMemberId = businessMemberId;
		this.email = email;
		this.password = password;
		this.businessLicense = businessLicense;
		this.businessMemberType = businessMemberType;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}

	@Builder(builderMethodName = "create")
	public BusinessMember(String email, String password, String businessLicense, BusinessMemberType businessMemberType) {
		this.email = email;
		this.password = password;
		this.businessLicense = businessLicense;
		this.businessMemberType = businessMemberType;
	}
}
