package org.h3t.example.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(access=AccessType.FIELD)
@SuppressWarnings( { "unused" })
public class Product implements Serializable {

	private static final long serialVersionUID = 4645886421995433897L;

	@Id	(generate=GeneratorType.AUTO)
	private int id;

	@Column(nullable = false)
	private String name;

	@ManyToMany(mappedBy = "products")
	private Collection<ProductCategory> categories;

	protected Product() {
	}

	public Product(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<ProductCategory> getCategories() {
		return categories;
	}

}
