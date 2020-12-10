package com.orderservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionResponse {
	protected OrderDto order;
	protected double amount;
	protected String transactionId;
	protected String message;
}
