package com.numble.reservation.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.numble.reservation.common.NumbleResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NumbleReservationException.class)
	public NumbleResponse<?> handleNumbleReservationException(NumbleReservationException exception) {
		return NumbleResponse.fail(exception);
	}

}
