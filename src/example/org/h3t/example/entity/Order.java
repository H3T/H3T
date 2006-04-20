package org.h3t.example.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.h3t.RemoteLoad;

@Entity(access=AccessType.FIELD)
@Table(name = "Orders")
@SuppressWarnings( { "unused" })
public class Order implements Serializable {

	private static final long serialVersionUID = -5333281223601855258L;

	@Id	(generate=GeneratorType.AUTO)
	private int id;

	@ManyToOne(optional = false)
	private Customer customer;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@RemoteLoad
	private Collection<OrderItem> items = new LinkedList<OrderItem>();

	@Version
	private int version;

	protected Order() {
	}

	public int getID() {
		return id;
	}

	public Order(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Collection<OrderItem> getItems() {
		return items;
	}

}
