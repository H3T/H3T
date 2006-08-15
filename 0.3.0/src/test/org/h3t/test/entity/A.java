package org.h3t.test.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class A {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public B b;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<B> bs = new LinkedList<B>();

}
