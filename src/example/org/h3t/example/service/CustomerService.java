package org.h3t.example.service;

import java.util.Date;
import java.util.List;

import org.h3t.example.entity.ClubMembership;
import org.h3t.example.entity.Customer;

public interface CustomerService {
	
	Customer findCustomer(String name);
	Customer createCustomer(String name);
	List<ClubMembership> getClubMembers();
	ClubMembership createClubMembership(Customer customer, Date date);
}
