package com.numble.reservation.service.performance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PerformanceRequest {
	@Size(min = 2, max = 128)
	private String name;
	@Min(1)
	private Integer capacity;
	@NotNull
	private LocalDateTime startAt;
	@NotNull
	private LocalDateTime endAt;
	@NotNull
	private BigDecimal normalPrice;
	@NotNull
	private BigDecimal vipPrice;
}
