package com.numble.reservation.persistence.spec;

import com.numble.reservation.persistence.entity.member.MemberEntity;

public class MemberEntitySpec {
	private MemberEntitySpec() {
	}

	public static MemberEntity of() {
		return MemberEntity.builder()
			.email("email@test.com")
			.password("password")
			.build();
	}
}
