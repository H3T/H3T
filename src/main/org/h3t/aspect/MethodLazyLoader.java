package org.h3t.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.LazyLoader;

import org.apache.log4j.Logger;
import org.h3t.util.LoadServiceFactoryMap;
import org.h3t.util.SerializableMethod;

public class MethodLazyLoader implements LazyLoader, Serializable {
	private final static Logger log = Logger.getLogger(MethodLazyLoader.class);
	private static final long serialVersionUID = 2021478396125896887L;

	private Object parentEntity;

	private SerializableMethod method;

	public MethodLazyLoader(Object parentEntity, Method method) {
		this.method = new SerializableMethod(method);
		this.parentEntity = parentEntity;
	}

	public Object loadObject() throws Exception {
		log.debug("Loading entity for " + method);
		return LoadServiceFactoryMap.getFactory(method.get()).lookup().loadAssociatedEntity(parentEntity, method);
	}

}

