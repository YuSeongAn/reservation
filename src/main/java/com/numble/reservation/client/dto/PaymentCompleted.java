package com.numble.reservation.client.dto;

import java.math.BigDecimal;
import java.time.Clock;

import org.springframework.context.ApplicationEvent;

import com.numble.reservation.domain.PaymentInfo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class PaymentCompleted {
	private Long memberId;
	private BigDecimal requestAmount;
	private PaymentInfo paymentInfo;

	@Builder
	public PaymentCompleted(Long memberId, BigDecimal requestAmount, PaymentInfo paymentInfo) {
		this.memberId = memberId;
		this.requestAmount = requestAmount;
		this.paymentInfo = paymentInfo;
	}
}
