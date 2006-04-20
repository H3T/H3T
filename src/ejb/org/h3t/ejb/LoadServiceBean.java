package org.h3t.ejb;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

import org.h3t.LoadService;
import org.h3t.Loader;
import org.h3t.util.SerializableField;
import org.h3t.util.SerializableMethod;
import org.hibernate.Session;

@Stateless
@Remote(LoadService.class)
public class LoadServiceBean implements LoadService {

	@PersistenceContext
	private Session session;

	public Collection loadAssociatedCollection(Object entity,
			SerializableMethod property) {
		return Loader.loadAssociatedCollection(session, entity, property.get());
	}

	public Collection loadAssociatedCollection(Object entity,
			SerializableField field) {
		return Loader.loadAssociatedCollection(session, entity, field.get());
	}

	public Object loadAssociatedEntity(Object entity,
			SerializableMethod property) {
		return Loader.loadAssociatedEntity(session, entity, property.get());
	}

	public Object loadAssociatedEntity(Object entity, SerializableField field) {
		return Loader.loadAssociatedEntity(session, entity, field.get());
	}

}
