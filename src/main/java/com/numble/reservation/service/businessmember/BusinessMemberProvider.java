package com.numble.reservation.service.businessmember;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.service.businessmember.dto.BusinessMemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessMemberProvider {
	private final BusinessMemberRepository businessMemberRepository;

	public BusinessMember getBusinessMember(Long businessMemberId) {
		return businessMemberRepository.findById(businessMemberId)
			.map(BusinessMemberEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.NOT_FOUND_ENTITY));
	}
}
