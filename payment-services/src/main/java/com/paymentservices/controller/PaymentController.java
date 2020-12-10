package com.paymentservices.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paymentservices.douments.Payment;
import com.paymentservices.dto.PaymentDto;
import com.paymentservices.service.PayementService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

	@Autowired
	private PayementService paymentService;
	
	@PreAuthorize("#oauth2.hasScope('write')")
	@PostMapping(path="/doPayment",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Mono<Payment>> doPayment(@Valid @RequestBody PaymentDto paymentDto) throws JsonProcessingException{
		log.info("recing request from order service"+paymentDto);
		Mono<Payment> payment=paymentService.doPayment(paymentDto);
		return new ResponseEntity<Mono<Payment>>(payment,HttpStatus.CREATED);
	}

	@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
	@GetMapping(path="/{paymentId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mono<Payment>> getPayment(@PathVariable(name = "paymentId",required = true) int id) throws JsonProcessingException {
		Mono<Payment> payment=paymentService.findPaymentHistoryByOrderId(id);
		return new ResponseEntity<Mono<Payment>>(payment,HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Payment>> getPayements(){
		Flux<Payment> payment=paymentService.findAllPayments();
		return new ResponseEntity<Flux<Payment>>(payment,HttpStatus.OK);
	}
}
