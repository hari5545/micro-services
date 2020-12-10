package com.paymentservices.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentservices.douments.Payment;
import com.paymentservices.dto.PaymentDto;
import com.paymentservices.repository.PaymentMongoRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class PaymentServiceImpl implements PayementService{

	@Autowired
	private  PaymentMongoRepository paymentMongoRepository;

	
	public String paymentProcessing(){
		//api should be 3rd party payment gateway (paypal,paytm...)
		return new Random().nextBoolean()?"success":"false";
	}

	@Override
	public Mono<Payment> doPayment(PaymentDto paymentDto) throws JsonProcessingException {
		Payment payment=null;
		payment=new Payment();
		payment.setOrderId(paymentDto.getOrderId());
		payment.setAmount(paymentDto.getAmount());
		payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionId(UUID.randomUUID().toString());
		payment.setId(new Random().nextInt(4));
		log.info("Payment-Service Request : {}",new ObjectMapper().writeValueAsString(payment));

		Mono<Payment> monoPayment=paymentMongoRepository.save(payment);
		return monoPayment;
	}


	@Override
	public Mono<Payment> findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
		Mono<Payment> payment=paymentMongoRepository.findByOrderId(orderId);
		log.info("paymentService findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
		return payment ;
	}


	@Override
	public Flux<Payment> findAllPayments() {
		return paymentMongoRepository.findAll();
	}
}
