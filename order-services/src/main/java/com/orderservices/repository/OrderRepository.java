package com.orderservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservices.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Integer>{

}
