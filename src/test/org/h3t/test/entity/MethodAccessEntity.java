package org.h3t.test.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.h3t.RemoteLoad;
import org.h3t.test.util.TestLoadServiceFactory;

@Entity(access = AccessType.PROPERTY)
@SuppressWarnings("unused")
public class MethodAccessEntity {

	private Integer id;

	private RelatedEntity oneToOneLazy;

	private RelatedEntity manyToOneLazy;

	private List<RelatedEntity> oneToManyLazy = new LinkedList<RelatedEntity>();;

	private List<RelatedEntity> manyToManyLazy = new LinkedList<RelatedEntity>();;

	private RelatedEntity oneToOneEager;

	private RelatedEntity manyToOneEager;

	private List<RelatedEntity> oneToManyEager = new LinkedList<RelatedEntity>();;

	private List<RelatedEntity> manyToManyEager = new LinkedList<RelatedEntity>();;

	@Id(generate = GeneratorType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity getOneToOneLazy() {
		return oneToOneLazy;
	}

	public void setOneToOneLazy(RelatedEntity r1) {
		this.oneToOneLazy = r1;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity getManyToOneLazy() {
		return manyToOneLazy;
	}

	public void setManyToOneLazy(RelatedEntity r2) {
		this.manyToOneLazy = r2;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> getOneToManyLazy() {
		return oneToManyLazy;
	}

	public void setOneToManyLazy(List<RelatedEntity> r3) {
		this.oneToManyLazy = r3;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> getManyToManyLazy() {
		return manyToManyLazy;
	}

	public void setManyToManyLazy(List<RelatedEntity> r4) {
		this.manyToManyLazy = r4;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity getOneToOneEager() {
		return oneToOneEager;
	}

	public void setOneToOneEager(RelatedEntity r1) {
		this.oneToOneEager = r1;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity getManyToOneEager() {
		return manyToOneEager;
	}

	public void setManyToOneEager(RelatedEntity r2) {
		this.manyToOneEager = r2;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> getOneToManyEager() {
		return oneToManyEager;
	}

	public void setOneToManyEager(List<RelatedEntity> r3) {
		this.oneToManyEager = r3;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> getManyToManyEager() {
		return manyToManyEager;
	}

	public void setManyToManyEager(List<RelatedEntity> r4) {
		this.manyToManyEager = r4;
	}

}