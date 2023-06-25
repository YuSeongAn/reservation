package com.numble.reservation.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numble.reservation.common.NumbleResponse;
import com.numble.reservation.domain.Member;
import com.numble.reservation.service.member.MemberProvider;
import com.numble.reservation.service.member.MemberService;
import com.numble.reservation.service.member.dto.MemberDto;
import com.numble.reservation.service.member.dto.MemberLoginRequest;
import com.numble.reservation.service.member.dto.MemberRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberProvider memberProvider;
	private final MemberService memberService;

	@PostMapping("/sign-up")
	public NumbleResponse<MemberDto> signUpMember(@RequestBody @Valid MemberRequest memberRequest) {
		final Member member = memberService.registerMember(memberRequest);

		return NumbleResponse.success(MemberDto.fromDomain(member));
	}

	@PostMapping("/login")
	public NumbleResponse<String> loginMember(@RequestBody MemberLoginRequest memberLoginRequest) {
		final String memberToken = memberService.loginMember(memberLoginRequest);

		return NumbleResponse.success(memberToken);
	}

	@GetMapping("/{memberId}")
	public NumbleResponse<MemberDto> getMember(@PathVariable("memberId") Long memberId) {
		final Member member = memberProvider.getMember(memberId);

		return NumbleResponse.success(MemberDto.fromDomain(member));
	}
}
