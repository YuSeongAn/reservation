package com.numble.reservation.persistence.spec;

import com.numble.reservation.code.BusinessMemberType;
import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;

public class BusinessMemberEntitySpec {
	private BusinessMemberEntitySpec() {
	}

	public static BusinessMemberEntity of(){
		return BusinessMemberEntity.builder()
			.businessLicense("businessLicense")
			.email("email@test.com")
			.password("password")
			.businessMemberType(BusinessMemberType.VENUE_ADMIN)
			.build();
	}

	public static BusinessMemberEntity ofVenueAdmin(){
		return BusinessMemberEntity.builder()
			.businessLicense("businessLicense")
			.email("email@test.com")
			.password("password")
			.businessMemberType(BusinessMemberType.VENUE_ADMIN)
			.build();
	}

	public static BusinessMemberEntity ofPerformanceAdmin(){
		return BusinessMemberEntity.builder()
			.businessLicense("businessLicense")
			.email("email@test.com")
			.password("password")
			.businessMemberType(BusinessMemberType.PERFORMANCE_ADMIN)
			.build();
	}
}
