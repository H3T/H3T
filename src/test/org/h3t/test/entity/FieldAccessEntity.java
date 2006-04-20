package org.h3t.test.entity;

import java.io.Serializable;
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

@Entity(access = AccessType.FIELD)
@SuppressWarnings("unused")
public class FieldAccessEntity implements Serializable {

	private static final long serialVersionUID = -1560874120075985801L;

	public @Id(generate = GeneratorType.AUTO)
	Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity oneToOneLazy;

	@ManyToOne(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity manyToOneLazy;

	@OneToMany(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> oneToManyLazy = new LinkedList<RelatedEntity>();

	@ManyToMany(fetch = FetchType.LAZY)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> manyToManyLazy = new LinkedList<RelatedEntity>();;

	@OneToOne(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity oneToOneEager;

	@ManyToOne(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public RelatedEntity manyToOneEager;

	@OneToMany(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> oneToManyEager = new LinkedList<RelatedEntity>();;

	@ManyToMany(fetch = FetchType.EAGER)
	@RemoteLoad(factory=TestLoadServiceFactory.class)
	public List<RelatedEntity> manyToManyEager = new LinkedList<RelatedEntity>();;

}