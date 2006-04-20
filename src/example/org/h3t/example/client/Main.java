package org.h3t.example.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.h3t.example.entity.Customer;
import org.h3t.example.entity.Order;
import org.h3t.example.entity.OrderItem;
import org.h3t.example.entity.Product;
import org.h3t.example.service.CustomerService;
import org.h3t.example.service.OrderService;
import org.h3t.example.service.ProductService;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Context ctx = new InitialContext();
		CustomerService cust_svc = (CustomerService) ctx
				.lookup(CustomerService.class.getName());
		OrderService ord_svc = (OrderService) ctx.lookup(OrderService.class
				.getName());
		ProductService prod_svc = (ProductService) ctx
				.lookup(ProductService.class.getName());

		Customer bob = cust_svc.createCustomer("Bob");
		Order order = ord_svc.createOrder(bob);
		Product hammer = prod_svc.createProduct("Hammer");
		order.getItems().add(new OrderItem(order, hammer, 2));
		ord_svc.updateOrder(order);

		for (Order ord : ord_svc.findCustomerOrders(bob)) {
			for (OrderItem item : ord.getItems()) {
				System.out.println(bob.getName() + " has ordered "
						+ item.getQuantity() + " of "
						+ item.getProduct().getName() + " in order "
						+ item.getOrder().getID());
			}
		}

	}
}
