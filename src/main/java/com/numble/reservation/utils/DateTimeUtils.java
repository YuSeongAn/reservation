package com.numble.reservation.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class DateTimeUtils {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_TIME;

	private DateTimeUtils() {
	}

	public static String getFrontTime(LocalTime localTime) {
		if (Objects.isNull(localTime)) {
			return StringUtils.EMPTY;
		}

		return timeFormatter.format(localTime);
	}

	public static String getFrontDateTime(LocalDateTime localDateTime) {
		if (Objects.isNull(localDateTime)) {
			return StringUtils.EMPTY;
		}

		return dateTimeFormatter.format(localDateTime);
	}
}
