package com.orderservices.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orderservices.dto.OrderDto;
import com.orderservices.dto.OrderRequest;
import com.orderservices.dto.TransactionResponse;

public interface OrderService {
	public TransactionResponse createOrder(OrderRequest orderRequest)  throws JsonProcessingException ;
	public OrderDto getOrder(int orderId);
	public List<OrderDto> getAllOrders();
}
