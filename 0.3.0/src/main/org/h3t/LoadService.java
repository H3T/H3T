package org.h3t;

import java.util.Collection;

import org.h3t.util.SerializableField;
import org.h3t.util.SerializableMethod;

/**
 * The <code>LoadService</code> interface is used by the client side of the
 * framework to load collections and entities from the server. The developer
 * should provide a custom implementation of this interface unless the provided
 * EJB ({@link org.h3t.ejb.LoadServiceBean}) is used.
 * 
 * @see org.h3t.LoadServiceFactory
 * @see org.h3t.ejb.LoadServiceBean
 * @see org.h3t.Loader
 * 
 * @author Rob Worsnop
 * 
 */
public interface LoadService {
	/**
	 * Loads a collection associated with an entity.
	 * 
	 * @param entity
	 *            The entity associated with the collection to be loaded.
	 * @param method
	 *            The method on the entity that returns the collection.
	 * @return The loaded collection.
	 */
	Collection loadAssociatedCollection(Object entity,
			SerializableMethod property);
	/**
	 * Loads a collection associated with an entity.
	 * 
	 * @param entity
	 *            The entity associated with the collection to be loaded.
	 * @param field
	 *            The field on the entity by which the collection is accessed.
	 * @return The loaded collection.
	 */
	Collection loadAssociatedCollection(Object entity, SerializableField field);
	/**
	 * Loads a child entity associated with an entity.
	 * 
	 * @param entity
	 *            The parent of the child entity to be loaded.
	 * @param method
	 *            The method on the entity that returns the child entity.
	 * @return The loaded child entity.
	 */
	Object loadAssociatedEntity(Object entity, SerializableMethod property);
	/**
	 * Loads a child entity associated with an entity.
	 * 
	 * @param entity
	 *            The parent of the child entity to be loaded.
	 * @param field
	 *            The field on the entity by which the child entity is accessed.
	 * @return The loaded child entity.
	 */
	Object loadAssociatedEntity(Object entity, SerializableField field);

}
