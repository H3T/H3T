package org.h3t.example.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.h3t.RemoteLoad;

@Entity
@SuppressWarnings({"unused"})
public class OrderItem implements Serializable{

	private static final long serialVersionUID = 6781802278020330507L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@RemoteLoad
	private Product product;
	
	@ManyToOne(optional=false)
	private Order order;
	
	@Column(nullable=false)
	private int quantity;

	protected OrderItem() {
	}

	public OrderItem(Order order, Product product, int quantity) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

}
