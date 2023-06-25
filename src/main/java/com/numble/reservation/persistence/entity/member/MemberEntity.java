package com.numble.reservation.persistence.entity.member;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.numble.reservation.domain.Member;
import com.numble.reservation.service.member.dto.MemberDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@EqualsAndHashCode(of = "memberId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column
	private String email;

	@Column
	private String password;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime lastModifiedAt;

	@Builder
	public MemberEntity(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public Member toDomain() {
		return Member.builder()
			.memberId(memberId)
			.email(email)
			.password(password)
			.createdAt(createdAt)
			.lastModifiedAt(lastModifiedAt)
			.build();
	}

	public static MemberEntity fromDomain(Member member) {
		if (Objects.isNull(member)) {
			return null;
		}

		return MemberEntity.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.build();
	}
}
