package org.h3t.test.entity;

import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(access=AccessType.FIELD)
@SuppressWarnings("unused")
public class B {
	@Id(generate=GeneratorType.AUTO)
	private Integer id;
	private String s;
	@ManyToOne
	private A a;
	@ManyToOne
	private C c;

	public B(String s) {
		this.s = s;
	}

	protected B() {
	}
	
	public Integer getId(){
		return id;
	}

	public String getS() {
		return s;
	}

}