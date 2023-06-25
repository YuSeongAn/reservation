package com.numble.reservation.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum NumbleErrorCode {
	NOT_FOUND_ENTITY("C001", "not found entity", HttpStatus.NOT_FOUND),
	INVALID_TOKEN("C002", "invalid token", HttpStatus.UNAUTHORIZED),
	EXPIRED_TOKEN("C003", "expired token", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED("C004", "unauthorized action", HttpStatus.UNAUTHORIZED),
	INVALID_VENUE_TIME_RANGE("V001", "invalid venue time range", HttpStatus.BAD_REQUEST),
	DUPLICATE_VENUE_SEAT("V002", "venue seatNumber is duplicated", HttpStatus.BAD_REQUEST),
	PERFORMANCE_CAPACITY_EXCEEDED("P001", "performance capacity exceeded venue's capacity", HttpStatus.BAD_REQUEST),
	INVALID_PERFORMANCE_PRICE("P002", "vip price must be greater or equal then normal price", HttpStatus.BAD_REQUEST),
	INVALID_PERFORMANCE_START_TIME_RANGE("P003", "performance started at future than present", HttpStatus.BAD_REQUEST),
	INVALID_PERFORMANCE_TIME_RANGE("P004", "performance started time must be future than end time", HttpStatus.BAD_REQUEST),
	PERFORMANCE_CAPACITY_OUT_OF_STOCK("P005", "reservation seat exceed limit", HttpStatus.BAD_REQUEST),
	PERFORMANCE_STARTED("P006", "performance started already", HttpStatus.BAD_REQUEST),
	RESERVATION_SEAT_EXCEED_LIMIT("R001", "reservation seat exceed limit", HttpStatus.BAD_REQUEST),
	RESERVATION_SEAT_OCCUPIED("R002", "reservation seat is occupied already", HttpStatus.BAD_REQUEST),
	ANOTHER_RESERVATION_PROCESSING("R003", "another reservation processing", HttpStatus.BAD_REQUEST);


	private String code;
	private String message;
	private HttpStatus httpStatus;

	NumbleErrorCode(String code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
