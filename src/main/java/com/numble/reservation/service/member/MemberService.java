package com.numble.reservation.service.member;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.Member;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.member.MemberEntity;
import com.numble.reservation.persistence.repository.member.MemberRepository;
import com.numble.reservation.service.member.dto.MemberLoginRequest;
import com.numble.reservation.service.member.dto.MemberRequest;
import com.numble.reservation.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member registerMember(MemberRequest memberRequest) {
		if (Objects.isNull(memberRequest)) {
			throw new NumbleReservationException("empty member request");
		}

		final String encryptedPassword = passwordEncoder.encode(memberRequest.getPassword());

		final Member member = Member.create()
			.email(memberRequest.getEmail())
			.password(encryptedPassword)
			.build();

		return memberRepository.save(MemberEntity.fromDomain(member)).toDomain();
	}

	public String loginMember(MemberLoginRequest memberLoginRequest) {
		if (Objects.isNull(memberLoginRequest)) {
			throw new NumbleReservationException("empty member login request");
		}

		final Member member = memberRepository.findByEmail(memberLoginRequest.getEmail())
			.map(MemberEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.UNAUTHORIZED));

		if (!passwordEncoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
			throw new NumbleReservationException(NumbleErrorCode.UNAUTHORIZED);
		}

		return JwtUtils.createToken(member.getMemberId());
	}
}
