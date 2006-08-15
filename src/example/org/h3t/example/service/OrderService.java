package org.h3t.example.service;

import java.util.List;

import org.h3t.example.entity.Customer;
import org.h3t.example.entity.Order;

public interface OrderService {
	
	List<Order> findCustomerOrders(Customer customer);
	Order createOrder(Customer customer);
	void updateOrder(Order order);

}
