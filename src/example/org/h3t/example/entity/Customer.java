package org.h3t.example.entity;

import java.io.Serializable;

import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;

@Entity(access=AccessType.FIELD)
@SuppressWarnings("unused")
public class Customer implements Serializable {

	private static final long serialVersionUID = -2916209260379724699L;

	@Id	(generate=GeneratorType.AUTO)
	private int id;

	@Column(nullable = false)
	private String name;

	protected Customer() {
	}

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
