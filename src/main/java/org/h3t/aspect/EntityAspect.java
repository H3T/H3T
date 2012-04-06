package org.h3t.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.security.auth.callback.Callback;

import org.h3t.util.BeanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EntityAspect implements LazyAspect{
	
	private final static Logger log = LoggerFactory.getLogger(EntityAspect.class);
	
	public Object invoke(FieldReadInvocation invocation) throws Throwable {
		Field field = invocation.getField();
		log.debug("Intercepting read of " + field);
		Object parentEntity = invocation.getTargetObject();
		Object ret = invocation.invokeNext();
		if (ret instanceof HibernateProxy && isLazy(field)){
			log.debug("Found lazy proxy for " + field);
			HibernateProxy proxy = (HibernateProxy)ret;
			if (proxy.getHibernateLazyInitializer().isUninitialized()){
				log.debug("Proxy not initialized. Creating proxy for " + field);
				Class[] interfaces = new Class[]{EntitySerializer.class};
				Callback[] callbacks = new Callback[]{new FieldLazyLoader(parentEntity, field), new SerializationInterceptor(ret)};
				ret = Enhancer.create(field.getType(), interfaces, new EntityCallbackFilter(), callbacks);
				field.set(parentEntity, ret);
			}
		}
		return ret;
	}


	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		log.debug("Intercepting invocation of " + method);
		Object parentEntity = invocation.getTargetObject();
		Object ret = invocation.invokeNext();
		if (ret instanceof HibernateProxy && isLazy(method)){
			log.debug("Found lazy proxy for " + method);
			HibernateProxy proxy = (HibernateProxy)ret;
			if (proxy.getHibernateLazyInitializer().isUninitialized()){
				log.debug("Proxy not initialized. Creating proxy for " + method);
				Class[] interfaces = new Class[]{EntitySerializer.class};
				Callback[] callbacks = new Callback[]{new MethodLazyLoader(parentEntity, method), new SerializationInterceptor(ret)};
				ret = Enhancer.create(method.getReturnType(), interfaces, new EntityCallbackFilter(), callbacks);
				new BeanProperty<Object>(method).set(parentEntity, ret);
			}
		}
		return ret;
	}
	

}

