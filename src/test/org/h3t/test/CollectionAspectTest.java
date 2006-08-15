package org.h3t.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.h3t.aspect.CollectionAspect;
import org.h3t.aspect.ManyToManyAspect;
import org.h3t.aspect.OneToManyAspect;
import org.h3t.test.entity.FieldAccessEntity;
import org.h3t.test.entity.MethodAccessEntity;
import org.h3t.test.util.HibernateMockFactory;
import org.h3t.test.util.TestLoadServiceFactory;
import org.h3t.util.BeanProperty;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;

public class CollectionAspectTest extends TestCase {

	protected void setUp() throws Exception {
	}

	protected void tearDown() throws Exception {
		TestLoadServiceFactory.clearQueue();
	}

	public void testField() throws Throwable {
		testField(new OneToManyAspect(), FieldAccessEntity.class
				.getField("oneToManyLazy"), ChildState.LAZY_UNINITIALIZED);
		testField(new ManyToManyAspect(), FieldAccessEntity.class
				.getField("manyToManyLazy"), ChildState.LAZY_UNINITIALIZED);
		testField(new OneToManyAspect(), FieldAccessEntity.class
				.getField("oneToManyLazy"), ChildState.LAZY_INITIALIZED);
		testField(new ManyToManyAspect(), FieldAccessEntity.class
				.getField("manyToManyLazy"), ChildState.LAZY_INITIALIZED);
	}

	public void testMethod() throws Throwable {
		testMethod(new OneToManyAspect(), MethodAccessEntity.class.getMethod(
				"getOneToManyLazy", new Class[0]),
				ChildState.LAZY_UNINITIALIZED);
		testMethod(new ManyToManyAspect(), MethodAccessEntity.class.getMethod(
				"getManyToManyLazy", new Class[0]),
				ChildState.LAZY_UNINITIALIZED);
		testMethod(new OneToManyAspect(), MethodAccessEntity.class.getMethod(
				"getOneToManyLazy", new Class[0]), ChildState.LAZY_INITIALIZED);
		testMethod(new ManyToManyAspect(), MethodAccessEntity.class.getMethod(
				"getManyToManyLazy", new Class[0]), ChildState.LAZY_INITIALIZED);

	}

	private void testField(CollectionAspect aspect, Field field,
			ChildState state) throws Throwable {
		boolean initialized = !state.equals(ChildState.LAZY_UNINITIALIZED);
		List loadedResult = HibernateMockFactory.newPersistentCollection(
				LinkedList.class, true);
		TestLoadServiceFactory.queueResult(loadedResult);
		FieldAccessEntity entity = new FieldAccessEntity();
		Collection originalValue = HibernateMockFactory
				.newPersistentCollection(LinkedList.class, initialized);
		field.set(entity, originalValue);
		FieldReadInvocation invocation = new FieldReadInvocation(field, 0,
				new Interceptor[0]);
		invocation.setTargetObject(entity);
		((Collection) aspect.proxyField(invocation)).iterator();
		if (initialized) {
			assertTrue(originalValue == aspect.proxyField(invocation));
		} else {
			assertTrue(loadedResult == aspect.proxyField(invocation));
		}
	}

	private void testMethod(CollectionAspect aspect, Method method,
			ChildState state) throws Throwable {
		boolean initialized = !state.equals(ChildState.LAZY_UNINITIALIZED);
		List loadedResult = HibernateMockFactory.newPersistentCollection(
				LinkedList.class, true);
		TestLoadServiceFactory.queueResult(loadedResult);
		MethodAccessEntity entity = new MethodAccessEntity();
		Collection originalValue = HibernateMockFactory
				.newPersistentCollection(LinkedList.class, initialized);
		new BeanProperty<Collection>(method).set(entity, originalValue);
		MethodInvocation invocation = new MethodInvocation(new Interceptor[0],
				0, method, method, null);
		invocation.setTargetObject(entity);
		((Collection) aspect.proxyProperty(invocation)).iterator();
		if (initialized) {
			assertTrue(originalValue == aspect.proxyProperty(invocation));
		} else {
			assertTrue(loadedResult == aspect.proxyProperty(invocation));
		}
	}

}
