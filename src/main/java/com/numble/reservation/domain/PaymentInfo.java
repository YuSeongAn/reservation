package com.numble.reservation.domain;

import java.time.LocalDateTime;

import com.numble.reservation.code.PaymentMethodType;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class PaymentInfo {
	private PaymentMethodType paymentMethodType;
	private String cardNumber;
	private LocalDateTime cardExpiredAt;
	private String cardCVC;
}
