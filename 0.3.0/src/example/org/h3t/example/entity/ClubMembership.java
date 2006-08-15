package org.h3t.example.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@SuppressWarnings( { "unused" })
public class ClubMembership implements Serializable {

	private static final long serialVersionUID = -3976265703305203845L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private Date joinDate;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private Customer customer;

	protected ClubMembership() {
	}

	public ClubMembership(Customer customer, Date joinDate) {
		this.customer = customer;
		this.joinDate = joinDate;
	}

	public Customer getCustomer() {
		return customer;
	}

}
