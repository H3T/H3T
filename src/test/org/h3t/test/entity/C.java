package org.h3t.test.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(access=AccessType.PROPERTY)
public class C {
	
	private Integer id;
	private List<B> bs = new LinkedList<B>();
	private B b;
	
	@Id(generate=GeneratorType.AUTO)
	public Integer getId(){
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Integer id){
		this.id = id;
	}
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public B getB(){
		return b;
	}
	
	public void setB(B b){
		this.b = b;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public List<B> getBs(){
		return bs;
	}
	
	@SuppressWarnings("unused")
	public void setBs(List<B> bs){
		this.bs = bs;
	}

}