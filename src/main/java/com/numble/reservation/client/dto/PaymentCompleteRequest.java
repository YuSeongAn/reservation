package com.numble.reservation.client.dto;

import java.math.BigDecimal;

import com.numble.reservation.domain.PaymentInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCompleteRequest {
	private Long memberId;
	private BigDecimal requestAmount;
	private PaymentInfo paymentInfo;
}
