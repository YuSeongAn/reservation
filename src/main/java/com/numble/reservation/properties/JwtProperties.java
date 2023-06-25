package com.numble.reservation.properties;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.numble.reservation.utils.JwtUtils;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private String secretKey;
	private String issuer;
	private Long validDurationSecond;

	@PostConstruct
	public void setUpJwtUtils(){
		JwtUtils.setJwtProperties(this);
	}
}
