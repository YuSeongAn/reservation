package com.numble.reservation.exception;

import com.numble.reservation.common.NumbleErrorCode;

import lombok.Getter;

@Getter
public class NumbleReservationException extends RuntimeException {
	private NumbleErrorCode errorCode;

	public NumbleReservationException(String message) {
		super(message);
	}

	public NumbleReservationException(NumbleErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
