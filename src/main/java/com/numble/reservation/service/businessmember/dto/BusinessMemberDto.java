package com.numble.reservation.service.businessmember.dto;

import java.util.Objects;

import com.numble.reservation.code.BusinessMemberType;
import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.utils.DateTimeUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessMemberDto {
	private Long businessMemberId;
	private String email;
	private String businessLicense;
	private BusinessMemberType businessMemberType;
	private String createdAt;
	private String lastModifiedAt;

	public static BusinessMemberDto fromDomain(BusinessMember businessMember) {
		if (Objects.isNull(businessMember)) {
			return null;
		}

		return BusinessMemberDto.builder()
			.businessMemberId(businessMember.getBusinessMemberId())
			.email(businessMember.getEmail())
			.businessLicense(businessMember.getBusinessLicense())
			.businessMemberType(businessMember.getBusinessMemberType())
			.createdAt(DateTimeUtils.getFrontDateTime(businessMember.getCreatedAt()))
			.lastModifiedAt(DateTimeUtils.getFrontDateTime(businessMember.getLastModifiedAt()))
			.build();
	}
}
