package com.orderservices.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.orderservices.dto.OrderDto;
import com.orderservices.dto.OrderRequest;
import com.orderservices.dto.TransactionResponse;
import com.orderservices.service.OrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/order")
public class OrderController {

	private final OrderService orderService;

	@PreAuthorize("#oauth2.hasScope('write')")
	@PostMapping(path = "/createOrder",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<TransactionResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) throws JsonProcessingException {
		TransactionResponse response=orderService.createOrder(orderRequest);
		return new ResponseEntity<TransactionResponse>(response,HttpStatus.CREATED);
	}

	@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
	@GetMapping(path="/getOrder/{orderId}",produces =MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<OrderDto> getOrder(@PathVariable(name ="orderId") int orderId) {
		OrderDto orderDto=null;
		orderDto=orderService.getOrder(orderId);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
	@GetMapping(path="/getOrders",produces =MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<OrderDto>> getAllOrders(){
		List<OrderDto> orderDtos=null;
		orderDtos=orderService.getAllOrders();
		return new ResponseEntity<List<OrderDto>>(orderDtos,HttpStatus.OK);
	}
}

