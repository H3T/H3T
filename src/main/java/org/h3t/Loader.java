package org.h3t;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.h3t.util.BeanProperty;
import org.h3t.util.FieldProperty;
import org.h3t.util.Property;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;

/**
 * This is a utility class that can be used by your implementation of
 * {@link org.h3t.LoadService}.
 * 
 * @author Rob Worsnop
 * 
 */
public class Loader {
	
	private static final Logger log = Logger.getLogger(Loader.class);

	private Loader() {
	}

	/**
	 * Loads a collection associated with an entity.
	 * 
	 * @param session
	 *            Hibernate session to use for loading the collection.
	 * @param entity
	 *            The entity associated with the collection to be loaded.
	 * @param method
	 *            The method on the entity that returns the collection.
	 * @return The loaded collection.
	 */
	public static Collection loadAssociatedCollection(Session session,
			Object entity, Method method) {
		log.debug("Loading collection using " + method);
		return load(session, entity, new BeanProperty(method),
				collectionInitializer());
	}

	/**
	 * Loads a collection associated with an entity.
	 * 
	 * @param session
	 *            Hibernate session to use for loading the collection.
	 * @param entity
	 *            The entity associated with the collection to be loaded.
	 * @param field
	 *            The field on the entity by which the collection is accessed.
	 * @return The loaded collection.
	 */
	public static Collection loadAssociatedCollection(Session session,
			Object entity, Field field) {
		log.debug("Loading collection using " + field);
		return load(session, entity, new FieldProperty(field),
				collectionInitializer());
	}

	/**
	 * Loads a child entity associated with an entity.
	 * 
	 * @param session
	 *            Hibernate session to use for loading the child entity.
	 * @param entity
	 *            The parent of the child entity to be loaded.
	 * @param method
	 *            The method on the entity that returns the child entity.
	 * @return The loaded child entity.
	 */
	public static Object loadAssociatedEntity(Session session, Object entity,
			Method method)  {
		log.debug("Loading entity using " + method);
		return load(session, entity, new BeanProperty(method),
				entityInitializer());
	}

	/**
	 * Loads a child entity associated with an entity.
	 * 
	 * @param session
	 *            Hibernate session to use for loading the collection.
	 * @param entity
	 *            The parent of the child entity to be loaded.
	 * @param field
	 *            The field on the entity by which the child entity is accessed.
	 * @return The loaded child entity.
	 */
	public static Object loadAssociatedEntity(Session session, Object entity,
			Field field)  {
		log.debug("Loading entity using " + field);
		return load(session, entity, new FieldProperty(field),
				entityInitializer());
	}

	private static <T> T load(Session session, Object entity,
			Property property, Initializer<T> initializer) {
		session.update(entity);
		return initializer.initialize(entity, property);
	}

	private static interface Initializer<T> {
		T initialize(Object entity, Property property);
	}

	private static Initializer<Collection> collectionInitializer() {
		return new Initializer<Collection>() {
			public Collection initialize(Object entity, Property property) {
				Collection coll = (Collection) property.get(entity);
				for (Object obj : coll) {
					if (obj instanceof HibernateProxy) {
						HibernateProxy proxy = (HibernateProxy) obj;
						if (proxy.getHibernateLazyInitializer()
								.isUninitialized()) {
							proxy.getHibernateLazyInitializer().initialize();
						}
					}
				}
				return coll;
			}
		};
	}

	private static Initializer<Object> entityInitializer() {
		return new Initializer<Object>() {

			public Object initialize(Object entity, Property property) {
				HibernateProxy proxy = (HibernateProxy) property.get(entity);
				proxy.getHibernateLazyInitializer().initialize();
				return proxy;
			}
		};
	}

}
