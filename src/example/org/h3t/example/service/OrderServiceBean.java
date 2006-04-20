package org.h3t.example.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.h3t.example.entity.Customer;
import org.h3t.example.entity.Order;

@SuppressWarnings("unchecked")
@Stateless
@Remote(OrderService.class)
public class OrderServiceBean implements OrderService {

	@PersistenceContext
	private EntityManager em;

	public List<Order> findCustomerOrders(Customer customer) {
		return (List<Order>) em.createQuery("from Order where customer = :customer")
				.setParameter("customer", customer).getResultList();
	}

	public Order createOrder(Customer customer) {
		Order ret = new Order(customer);
		em.persist(ret);
		return ret;
	}

	public void updateOrder(Order order) {
		em.merge(order);
	}

}
