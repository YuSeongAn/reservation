package com.numble.reservation.service.businessmember;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.service.businessmember.dto.BusinessMemberLoginRequest;
import com.numble.reservation.service.businessmember.dto.BusinessMemberRequest;
import com.numble.reservation.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessMemberService {
	private final BusinessMemberRepository businessMemberRepository;
	private final PasswordEncoder passwordEncoder;

	public BusinessMember registerBusinessMember(BusinessMemberRequest businessMemberRequest) {
		if (Objects.isNull(businessMemberRequest)) {
			throw new NumbleReservationException("empty business member request");
		}

		final String encryptedPassword = passwordEncoder.encode(businessMemberRequest.getPassword());

		final BusinessMember businessMember = BusinessMember.create()
			.email(businessMemberRequest.getEmail())
			.password(encryptedPassword)
			.businessMemberType(businessMemberRequest.getBusinessMemberType())
			.businessLicense(businessMemberRequest.getBusinessLicense())
			.build();

		return businessMemberRepository.save(BusinessMemberEntity.fromDomain(businessMember)).toDomain();
	}

	public String loginBusinessMember(BusinessMemberLoginRequest businessMemberLoginRequest) {
		if (Objects.isNull(businessMemberLoginRequest)) {
			throw new NumbleReservationException("empty business member login request");
		}

		final BusinessMember businessMember = businessMemberRepository.findByEmail(businessMemberLoginRequest.getEmail())
			.map(BusinessMemberEntity::toDomain)
			.orElseThrow(() -> new NumbleReservationException(NumbleErrorCode.UNAUTHORIZED));

		if (!passwordEncoder.matches(businessMemberLoginRequest.getPassword(), businessMember.getPassword())) {
			throw new NumbleReservationException(NumbleErrorCode.UNAUTHORIZED);
		}

		return JwtUtils.createToken(businessMember.getBusinessMemberId());
	}
}
