package org.h3t.test.entity;

import java.io.Serializable;

import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;

@Entity(access=AccessType.FIELD)
@SuppressWarnings("unused")
public class RelatedEntity implements Serializable {

	private static final long serialVersionUID = 6740944970089887263L;
	
	@Id(generate=GeneratorType.AUTO)
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
