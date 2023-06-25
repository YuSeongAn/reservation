package com.numble.reservation.client;

import org.springframework.stereotype.Component;

import com.numble.reservation.client.dto.PaymentCancelRequest;
import com.numble.reservation.client.dto.PaymentCompleteRequest;
import com.numble.reservation.client.dto.PaymentResponse;

@Component
public class PaymentGatewayApiClient {
	public PaymentResponse completePayment(PaymentCompleteRequest paymentCompleteRequest) {
		try {
			Thread.sleep(300L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return new PaymentResponse(true);
	}

	public PaymentResponse cancelPayment(PaymentCancelRequest paymentCancelRequest)  {
		try {
			Thread.sleep(300L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return new PaymentResponse(true);
	}
}
