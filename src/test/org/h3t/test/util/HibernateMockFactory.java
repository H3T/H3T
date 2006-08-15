package org.h3t.test.util;

import java.io.Serializable;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.hibernate.HibernateException;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class HibernateMockFactory {
	private HibernateMockFactory() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T newPersistentCollection(Class<T> superclass, boolean initialized) {
		Class[] interfaces = new Class[] { PersistentCollection.class};
		Callback[] callbacks = new Callback[]{NoOp.INSTANCE, wasInitializedInterceptor(initialized)};
		return (T)Enhancer.create(superclass, interfaces, persistentCollectionFilter(), callbacks);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newHibernateProxy(Class<T> superclass, boolean initialized) {
		Class[] interfaces = new Class[] { HibernateProxy.class };
		Callback[] callbacks = new Callback[] { NoOp.INSTANCE,
				hibernateProxyInterceptor(initialized) };
		return (T) Enhancer.create(superclass, interfaces,
				hibernateProxyFilter(), callbacks);

	}

	private static MethodInterceptor hibernateProxyInterceptor(final boolean initialized) {
		return new MethodInterceptor() {

			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {
				return proxy.invoke(hibernateProxy(initialized), args);
			}
		};
	}

	private static HibernateProxy hibernateProxy(final boolean initialized) {
		return new HibernateProxy() {
			private static final long serialVersionUID = 7354735552981279856L;

			public Object writeReplace() {
				return null;
			}

			public LazyInitializer getHibernateLazyInitializer() {
				return lazyInitializer(initialized);
			}

		};
	}

	private static LazyInitializer lazyInitializer(final boolean initialized) {
		return new LazyInitializer() {

			public void initialize() throws HibernateException {
			}

			public Serializable getIdentifier() {
				return null;
			}

			public void setIdentifier(Serializable arg0) {
			}

			public String getEntityName() {
				return null;
			}

			public Class getPersistentClass() {
				return null;
			}

			public boolean isUninitialized() {
				return !initialized;
			}

			public void setImplementation(Object arg0) {
			}

			public SessionImplementor getSession() {
				return null;
			}

			public void setSession(SessionImplementor arg0)
					throws HibernateException {
			}

			public Object getImplementation() {
				return null;
			}

			public Object getImplementation(SessionImplementor arg0)
					throws HibernateException {
				return null;
			}

			public void setUnwrap(boolean arg0) {
				// TODO Auto-generated method stub
				
			}

			public boolean isUnwrap() {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

	private static MethodInterceptor wasInitializedInterceptor(final boolean initialized) {
		return new MethodInterceptor() {
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return new Boolean(initialized);
			}
		};
	}

	private static CallbackFilter persistentCollectionFilter() {
		return new CallbackFilter() {

			public int accept(Method method) {
				
				try {
					Method wasInitialized = PersistentCollection.class.getDeclaredMethod("wasInitialized", new Class[0]);

					if (method.equals(wasInitialized)) {
						return 1;
					} else {
						return 0;
					}
				} catch (NoSuchMethodException e) {
					throw new AssertionError(e);
				}
			}
		};
	}

	private static CallbackFilter hibernateProxyFilter() {
		return new CallbackFilter() {

			public int accept(Method method) {
				if (method.getDeclaringClass().equals(HibernateProxy.class)) {
					return 1;
				} else {
					return 0;
				}
			}
		};
	}
}
