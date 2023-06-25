package com.numble.reservation.common;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.numble.reservation.exception.NumbleReservationException;

import lombok.Builder;
import lombok.Data;

@Data
public class NumbleResponse<T> {
	private Boolean success;
	private T result;
	private Integer status;
	private NumbleError error;

	@Data
	@Builder
	private static class NumbleError {
		private String reason;
		private String code;
	}

	public static <T> NumbleResponse<T> success(T result) {
		final NumbleResponse<T> numbleResponse = new NumbleResponse<>();

		numbleResponse.setSuccess(Boolean.TRUE);
		numbleResponse.setResult(result);
		numbleResponse.setStatus(HttpStatus.OK.value());
		numbleResponse.setError(null);

		return numbleResponse;
	}

	public static <T> NumbleResponse<T> fail(NumbleReservationException exception) {
		final NumbleResponse<T> numbleResponse = new NumbleResponse<>();
		final NumbleErrorCode errorCode = exception.getErrorCode();

		final NumbleError numbleError = NumbleError.builder()
			.reason(Optional.ofNullable(errorCode)
				.map(NumbleErrorCode::getMessage)
				.orElse(StringUtils.EMPTY))
			.code(Optional.ofNullable(errorCode)
				.map(NumbleErrorCode::getCode)
				.orElse(null))
			.build();

		numbleResponse.setSuccess(false);
		numbleResponse.setResult(null);
		numbleResponse.setStatus(Optional.ofNullable(errorCode)
			.map(NumbleErrorCode::getHttpStatus)
			.map(HttpStatus::value)
			.orElse(null));
		numbleResponse.setError(numbleError);

		return numbleResponse;
	}
}
