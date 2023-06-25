package com.numble.reservation.utils;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.numble.reservation.common.NumbleErrorCode;
import com.numble.reservation.exception.NumbleReservationException;
import com.numble.reservation.properties.JwtProperties;

public class JwtUtils {
	private static final String JWT_TOKEN_CLAIM_ID_NAME = "id";

	private static JwtProperties jwtProperties;

	private JwtUtils() {
	}

	public static void setJwtProperties(JwtProperties jwtProperties) {
		JwtUtils.jwtProperties = jwtProperties;
	}

	public static String createToken(Long id) {
		final Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());

		return JWT.create()
			.withClaim(JWT_TOKEN_CLAIM_ID_NAME, id)
			.sign(algorithm);
	}

	public static Long getIdFromToken(String jwtToken) {
		final Map<String, Claim> claimMap = decodeJwtToken(jwtToken);

		return claimMap.get(JWT_TOKEN_CLAIM_ID_NAME).asLong();
	}

	private static Map<String, Claim> decodeJwtToken(String jwtToken) {
		final Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
		final JWTVerifier verifier = JWT.require(algorithm)
			.acceptExpiresAt(jwtProperties.getValidDurationSecond())
			.build();

		try {
			final DecodedJWT decodedJWT = verifier.verify(jwtToken);

			return decodedJWT.getClaims();
		} catch (SignatureVerificationException e) {
			throw new NumbleReservationException(NumbleErrorCode.INVALID_TOKEN);
		} catch (TokenExpiredException e) {
			throw new NumbleReservationException(NumbleErrorCode.EXPIRED_TOKEN);
		}
	}
}
