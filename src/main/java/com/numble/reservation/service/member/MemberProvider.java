package com.numble.reservation.service.member;

import org.springframework.stereotype.Service;

import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.Member;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.member.MemberEntity;
import com.numble.reservation.persistence.repository.member.MemberRepository;
import com.numble.reservation.service.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberProvider {
	private final MemberRepository memberRepository;

	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.map(MemberEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));
	}
}
