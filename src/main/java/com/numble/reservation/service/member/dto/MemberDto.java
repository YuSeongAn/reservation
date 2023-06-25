package com.numble.reservation.service.member.dto;

import java.util.Objects;

import com.numble.reservation.domain.Member;
import com.numble.reservation.utils.DateTimeUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
	private Long memberId;
	private String email;
	private String createdAt;
	private String lastModifiedAt;

	public static MemberDto fromDomain(Member member) {
		if (Objects.isNull(member)) {
			return null;
		}

		return MemberDto.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.createdAt(DateTimeUtils.getFrontDateTime(member.getCreatedAt()))
			.lastModifiedAt(DateTimeUtils.getFrontDateTime(member.getLastModifiedAt()))
			.build();
	}
}
