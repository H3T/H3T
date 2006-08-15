package org.h3t.example.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.h3t.example.entity.ClubMembership;
import org.h3t.example.entity.Customer;

@Stateless
@Remote(CustomerService.class)
public class CustomerServiceBean implements CustomerService {

	@PersistenceContext
	private EntityManager em;

	public Customer findCustomer(String name) {
		return (Customer) em.createQuery("from Customer where name = :name")
				.setParameter("name", name).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<ClubMembership> getClubMembers() {
		return (List<ClubMembership>) em.createQuery("from ClubMembership")
				.getResultList();
	}

	public Customer createCustomer(String name) {
		Customer ret = new Customer(name);
		em.persist(ret);
		return ret;
	}

	public ClubMembership createClubMembership(Customer customer, Date date) {
		ClubMembership ret = new ClubMembership(customer, date);
		em.persist(ret);
		return ret;
	}
}
