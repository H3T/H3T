package org.h3t.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.h3t.aspect.CollectionAspect;
import org.h3t.aspect.ManyToManyAspect;
import org.h3t.aspect.OneToManyAspect;
import org.h3t.test.entity.FieldAccessEntity;
import org.h3t.test.entity.MethodAccessEntity;
import org.h3t.test.util.HibernateMockFactory;
import org.h3t.test.util.TestLoadServiceFactory;
import org.h3t.util.BeanProperty;
import org.junit.After;
import org.junit.Test;
public class CollectionAspectTest {


	@After
	public void tearDown() throws Exception {
		TestLoadServiceFactory.clearQueue();
	}
	
	@Test
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
	@Test
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
		ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
		FieldSignature signature = mock(FieldSignature.class);
		when(signature.getField()).thenReturn(field);
		when(joinPoint.getSignature()).thenReturn(signature);
		when(joinPoint.getTarget()).thenReturn(entity);
		when(joinPoint.proceed()).thenReturn(originalValue);
		((Collection) aspect.proxyField(joinPoint)).iterator();
		if (initialized) {
			assertTrue(originalValue == aspect.proxyField(joinPoint));
		} else {
			assertTrue(loadedResult == aspect.proxyField(joinPoint));
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
		ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
		MethodSignature signature = mock(MethodSignature.class);
		when(signature.getMethod()).thenReturn(method);
		when(joinPoint.getSignature()).thenReturn(signature);
		when(joinPoint.getTarget()).thenReturn(entity);
		when(joinPoint.proceed()).thenReturn(originalValue);
		((Collection) aspect.proxyProperty(joinPoint)).iterator();
		if (initialized) {
			assertTrue(originalValue == aspect.proxyProperty(joinPoint));
		} else {
			assertTrue(loadedResult == aspect.proxyProperty(joinPoint));
		}
	}

}
