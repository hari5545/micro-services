package com.orderservices.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservices.client.PaymentServices;
import com.orderservices.dto.OrderDto;
import com.orderservices.dto.OrderRequest;
import com.orderservices.dto.PaymentDto;
import com.orderservices.dto.TransactionResponse;
import com.orderservices.entity.Order;
import com.orderservices.repository.OrderRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

	private final OrderRepository orderRepository;
	private final ModelMapper modelMapper;
	private final RestTemplate restTemplate;
    private final PaymentServices paymentServices;
	private final String ENDPOINT_URL="http://localhost:8084/api/payment/doPayment";

	@Override
	public TransactionResponse createOrder(OrderRequest orderRequest) throws JsonProcessingException {
		String response = "";
		OrderDto orderDto = orderRequest.getOrder();
		PaymentDto payment = orderRequest.getPayment();
		payment.setOrderId(orderDto.getId());
		payment.setAmount(orderDto.getPrice());
       // getting jwt token from securitycontextholder 
		final OAuth2AuthenticationDetails details =(OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
		String  tokenValue = details.getTokenValue();
		log.info("jwt token value"+tokenValue);
		if(tokenValue!=null) {
			// making rest calls using rest template
			
		/*	HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization","Bearer "+tokenValue);
			HttpEntity<PaymentDto> request = new HttpEntity<>(payment,headers);
			//rest call
			log.info("Order-Service Request : "+new ObjectMapper().writeValueAsString(orderRequest));
			PaymentDto paymentResponse = restTemplate.postForObject(ENDPOINT_URL, request, PaymentDto.class);
		 */
			
			// making rest call using feign client
			
			String jwtToken="Bearer "+tokenValue;		
			PaymentDto paymentResponse=paymentServices.doPayment(jwtToken,payment);
			
			response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "there is a failure in payment api , order added to cart";

			log.info("Order Service getting Response from Payment-Service : "+new ObjectMapper().writeValueAsString(response));
			Order order= convertDtoToEntity(orderDto);
			log.info("order object after conversion"+order);
			Order orderResponse=orderRepository.save(order);
			OrderDto orderDto2= convertOrderEntityToDto(orderResponse);
			return new TransactionResponse(orderDto2, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
		}
		return null;
	}

	@Override
	public OrderDto getOrder(int orderId) {
		OrderDto orderDto = null;
		Optional<Order> order=orderRepository.findById(orderId);
		log.info("order object based on order id"+order.get());
		if(order.get()!= null ) {
			orderDto=convertOrderEntityToDto(order.get());
		}
		return orderDto; 
	}

	@Override
	public List<OrderDto> getAllOrders() {
		List<OrderDto> orderDtos=null;
		List<Order> orderList=orderRepository.findAll();
		log.info("all order objects"+orderList);
		orderDtos=orderList.stream().map(this::convertOrderEntityToDto).collect(Collectors.toList());
		return orderDtos;
	}
	public OrderDto convertOrderEntityToDto(Order order){
		OrderDto orderDto=modelMapper.map(order,OrderDto.class);
		return orderDto;
	}

	public Order convertDtoToEntity(OrderDto orderDto) {
		Order order=modelMapper.map(orderDto, Order.class);
		return order;
	}

}
