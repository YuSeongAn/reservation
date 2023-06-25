package com.numble.reservation.client.dto;

import java.math.BigDecimal;

import com.numble.reservation.domain.PaymentInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentCancelRequest {
	private Long memberId;
	private BigDecimal requestAmount;
	private PaymentInfo paymentInfo;
}
