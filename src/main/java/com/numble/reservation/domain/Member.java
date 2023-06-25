package com.numble.reservation.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
	private Long memberId;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

	@Builder(builderMethodName = "builder")
	public Member(Long memberId, String email, String password, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}

	@Builder(builderMethodName = "create")
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
