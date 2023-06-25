package com.numble.reservation.resolver;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.numble.reservation.domain.BusinessMember;
import com.numble.reservation.service.businessmember.BusinessMemberProvider;
import com.numble.reservation.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessMemberArgumentResolver implements HandlerMethodArgumentResolver {
	private static final String AUTHORIZATION_HEADER_NAME = "X-Authorization";

	private final BusinessMemberProvider businessMemberProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		return parameter.getParameterType().equals(BusinessMember.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (Objects.isNull(servletRequestAttributes)) {
			return null;
		}

		final String authorization = servletRequestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER_NAME);

		final Long businessMemberId = JwtUtils.getIdFromToken(authorization);

		return businessMemberProvider.getBusinessMember(businessMemberId);
	}

}
