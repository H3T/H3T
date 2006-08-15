package org.h3t.test.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@SuppressWarnings("unused")
public class RelatedEntity implements Serializable {

	private static final long serialVersionUID = 6740944970089887263L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String s;

	protected RelatedEntity() {
	}

	public RelatedEntity(String s) {
		this.s = s;
	}

	public String s() {
		return s;
	}

}
