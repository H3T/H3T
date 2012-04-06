package org.h3t.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;

import org.h3t.LoadService;
import org.h3t.RemoteLoad;
import org.h3t.util.BeanProperty;
import org.h3t.util.FieldProperty;
import org.h3t.util.LoadServiceFactoryMap;
import org.h3t.util.Property;
import org.h3t.util.SerializableField;
import org.h3t.util.SerializableMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CollectionAspect implements LazyAspect {
	private static final Logger log = LoggerFactory.getLogger(CollectionAspect.class);

	public Object proxyField(FieldReadInvocation invocation) throws Throwable {
		Field field = invocation.getField();
		log.debug("Intercepting read of " + field);
		Object entity = invocation.getTargetObject();
		Object ret = invocation.invokeNext();
		if (ret instanceof PersistentCollection && isLazy(field)) {
			log.debug("Found lazy collection for " + field);
			PersistentCollection collection = (PersistentCollection) ret;
			if (!collection.wasInitialized()) {
				log.debug("Collection not initialized. Creating proxy for "
						+ field);
				ret = newProxyInstance(field.getType(), fieldHandler(entity,
						field, collection));
			}
		}
		return ret;
	}

	public Object proxyProperty(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		log.debug("Intercepting invocation of " + method);
		Object entity = invocation.getTargetObject();
		Object ret = invocation.invokeNext();
		if (ret instanceof PersistentCollection && isLazy(method)) {
			log.debug("Found lazy collection for " + method);
			PersistentCollection collection = (PersistentCollection) ret;
			if (!collection.wasInitialized()) {
				log.debug("Collection not initialized. Creating proxy for "
						+ method);
				ret = newProxyInstance(method.getReturnType(), propertyHandler(
						entity, method, collection));
			}
		}
		return ret;
	}

	private static Object newProxyInstance(Class type, InvocationHandler handler) {
		return Proxy.newProxyInstance(Thread.currentThread()
				.getContextClassLoader(), new Class[] { type }, handler);
	}

	private static InvocationHandler fieldHandler(final Object entity,
			final Field field, PersistentCollection collection) {
		return new AbstractHandler(entity, new FieldProperty<Object>(field),
				collection) {
			@Override
			Collection load(LoadService service)
					throws InvocationTargetException {
				log.debug("Loading collection in " + field);
				return service.loadAssociatedCollection(entity,
						new SerializableField(field));
			}
		};
	}

	private static InvocationHandler propertyHandler(final Object entity,
			final Method properyGetter, PersistentCollection collection) {
		return new AbstractHandler(entity, new BeanProperty<Object>(
				properyGetter), collection) {
			@Override
			Collection load(LoadService service)
					throws InvocationTargetException {
				log.debug("Loading collection in " + properyGetter);
				return service.loadAssociatedCollection(entity,
						new SerializableMethod(properyGetter));
			}
		};
	}

	private static abstract class AbstractHandler implements InvocationHandler {

		private Object entity;

		private Property<Object> property;

		private PersistentCollection collection;

		private AbstractHandler(Object entity, Property<Object> property,
				PersistentCollection collection) {
			this.entity = entity;
			this.property = property;
			this.collection = collection;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			try {
				if (!collection.wasInitialized()) {
					log
							.debug("Collection found not initialized during invocation of "
									+ method);
					collection = (PersistentCollection) load(LoadServiceFactoryMap
							.getFactory(
									property.getAnnotation(RemoteLoad.class)
											.factory()).lookup());
					property.set(entity, collection);
				}
				return method.invoke(collection, args);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		abstract Collection load(LoadService service)
				throws InvocationTargetException;

	}
}
