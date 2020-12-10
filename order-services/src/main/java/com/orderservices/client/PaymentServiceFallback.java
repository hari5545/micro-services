package com.orderservices.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservices.dto.PaymentDto;

@Component
public class PaymentServiceFallback implements PaymentServices{

	private static final Logger log = LoggerFactory.getLogger(PaymentServiceFallback.class);
	
	@Override
	public PaymentDto doPayment(String jwtToken, PaymentDto paymentDto) throws JsonProcessingException {
		log.info("payment service fallback inside order services");
		PaymentDto fallbackPaymentDto=new PaymentDto();
		fallbackPaymentDto.setAmount(paymentDto.getAmount());
		fallbackPaymentDto.setId(paymentDto.getId());
		fallbackPaymentDto.setOrderId(paymentDto.getOrderId());
		fallbackPaymentDto.setPaymentStatus("falied");
		fallbackPaymentDto.setTransactionId(UUID.randomUUID().toString());
		log.info("fallback payment dto object"+new ObjectMapper().writeValueAsString(fallbackPaymentDto));
		return fallbackPaymentDto;
	}
	
}
