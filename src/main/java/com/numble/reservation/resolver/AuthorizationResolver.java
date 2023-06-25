package com.numble.reservation.resolver;

import java.util.Objects;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthorizationResolver {
	private static final String AUTHORIZATION_HEADER_NAME = "X-Authorization";

	private AuthorizationResolver() {}

	public static Long resolveAuthorization() {
		final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (Objects.isNull(servletRequestAttributes)) {
			return null;
		}

		final String authorizationHeaderValue = servletRequestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER_NAME);

		if (Objects.isNull(authorizationHeaderValue)) {
			return null;
		}

		//토큰 디코딩 후 long return 해야함.
		return 0L;
	}
}
