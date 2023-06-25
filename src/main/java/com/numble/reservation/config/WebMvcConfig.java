package com.numble.reservation.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.numble.reservation.resolver.BusinessMemberArgumentResolver;
import com.numble.reservation.resolver.MemberArgumentResolver;
import com.numble.reservation.service.businessmember.BusinessMemberProvider;
import com.numble.reservation.service.member.MemberProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final BusinessMemberProvider businessMemberProvider;
	private final MemberProvider memberProvider;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new BusinessMemberArgumentResolver(businessMemberProvider));
		resolvers.add(new MemberArgumentResolver(memberProvider));
	}
}
