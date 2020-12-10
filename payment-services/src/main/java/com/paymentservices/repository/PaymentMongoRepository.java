package com.paymentservices.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.paymentservices.douments.Payment;

import reactor.core.publisher.Mono;

public interface PaymentMongoRepository extends ReactiveMongoRepository<Payment, Integer>{

	Mono<Payment> findByOrderId(int orderId);

}
