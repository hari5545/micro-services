package com.orderservices.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDto {
 
	protected int id;
	
	@NotEmpty(message = "name is required")
    protected String name;	
	@NotEmpty(message = "quantity required")
    protected int quantity;
	@NotEmpty(message = "price required")
    protected double price;
}
