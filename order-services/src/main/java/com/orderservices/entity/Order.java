package com.orderservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="order_table")
public class Order implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id",insertable = false,updatable = false,unique = true,nullable = false)
	protected int id;
	
	@Column(name="name",nullable = false,length = 255)
    protected String name;
	
	@Column(name="quantity",length = 50)
    protected int quantity;
	
	@Column(name="price",length = 50)
    protected double price;
}
