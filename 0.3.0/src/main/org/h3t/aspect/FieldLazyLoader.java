package org.h3t.aspect;

import java.io.Serializable;
import java.lang.reflect.Field;

import net.sf.cglib.proxy.LazyLoader;

import org.apache.log4j.Logger;
import org.h3t.util.LoadServiceFactoryMap;
import org.h3t.util.SerializableField;

public class FieldLazyLoader implements LazyLoader, Serializable {
	private final static Logger log = Logger.getLogger(FieldLazyLoader.class);

	private static final long serialVersionUID = -1783247377346260428L;

	private Object parentEntity;

	private SerializableField field;

	public FieldLazyLoader(Object parentEntity, Field field) {
		this.field = new SerializableField(field);
		this.parentEntity = parentEntity;
	}

	public Object loadObject() throws Exception {
		log.debug("Loading entity for " + field);
		return LoadServiceFactoryMap.getFactory(field.get()).lookup()
				.loadAssociatedEntity(parentEntity, field);
	}

}
