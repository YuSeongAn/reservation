package com.numble.reservation.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.reservation.common.NumbleResponse;
import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.service.businessmember.BusinessMemberProvider;
import com.numble.reservation.service.businessmember.BusinessMemberService;
import com.numble.reservation.service.businessmember.dto.BusinessMemberDto;
import com.numble.reservation.service.businessmember.dto.BusinessMemberLoginRequest;
import com.numble.reservation.service.businessmember.dto.BusinessMemberRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/business-member")
@RequiredArgsConstructor
public class BusinessMemberController {
	private final BusinessMemberProvider businessMemberProvider;
	private final BusinessMemberService businessMemberService;

	@PostMapping("/sign-up")
	public NumbleResponse<BusinessMemberDto> signUpBusinessMember(@RequestBody @Valid BusinessMemberRequest businessMemberRequest) {
		final BusinessMember businessMember = businessMemberService.registerBusinessMember(businessMemberRequest);

		return NumbleResponse.success(BusinessMemberDto.fromDomain(businessMember));
	}

	@PostMapping("/login")
	public NumbleResponse<String> loginBusinessMember(@RequestBody BusinessMemberLoginRequest businessMemberLoginRequest) {
		final String businessMemberToken = businessMemberService.loginBusinessMember(businessMemberLoginRequest);

		return NumbleResponse.success(businessMemberToken);
	}

	@GetMapping("/{businessMemberId}")
	public NumbleResponse<BusinessMemberDto> getBusinessMember(@PathVariable("businessMemberId") Long businessMemberId) {
		final BusinessMember businessMember = businessMemberProvider.getBusinessMember(businessMemberId);

		return NumbleResponse.success(BusinessMemberDto.fromDomain(businessMember));
	}
}
