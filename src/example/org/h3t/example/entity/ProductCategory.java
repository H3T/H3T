package org.h3t.example.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(access=AccessType.FIELD)
@SuppressWarnings( { "unused" })
public class ProductCategory implements Serializable {

	private static final long serialVersionUID = -2615892973043707556L;

	@Id	(generate=GeneratorType.AUTO)
	private int id;

	@Column(nullable = false)
	private String name;

	@ManyToMany(targetEntity = Product.class)
	private Collection<Product> products = new LinkedList<Product>();

	protected ProductCategory() {
	}

	public ProductCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<Product> getProducts() {
		return products;
	}

}
