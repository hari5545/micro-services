package com.paymentservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentDto {
	protected int id;
	protected String paymentStatus;
	protected String transactionId;
	protected int orderId;
	protected double amount;
}
