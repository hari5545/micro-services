package com.paymentservices.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paymentservices.douments.Payment;
import com.paymentservices.dto.PaymentDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PayementService {


	public Mono<Payment> doPayment(PaymentDto paymentDto) throws JsonProcessingException;
	public Mono<Payment> findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException;
	public Flux<Payment> findAllPayments();

}
