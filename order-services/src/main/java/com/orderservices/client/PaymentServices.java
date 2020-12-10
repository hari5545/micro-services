package com.orderservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orderservices.dto.PaymentDto;

@FeignClient(name="payment-service",url="http://localhost:8084",fallback = PaymentServiceFallback.class)
public interface PaymentServices {
	
	@PostMapping(path = "/api/payment/doPayment", consumes = MediaType.APPLICATION_JSON_VALUE)
	public PaymentDto doPayment(@RequestHeader("Authorization") String jwtToken,@RequestBody PaymentDto paymentDto) throws JsonProcessingException;

}
