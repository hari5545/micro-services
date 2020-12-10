package com.paymentservices.douments;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "payment")
public class Payment {
	@Id
	protected int id;
	protected String paymentStatus;
	protected String transactionId;
	protected int orderId;
	protected double amount;
}
