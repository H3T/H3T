package org.h3t.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.h3t.aspect.EntityAspect;
import org.h3t.aspect.ManyToOneAspect;
import org.h3t.aspect.OneToOneAspect;
import org.h3t.test.entity.FieldAccessEntity;
import org.h3t.test.entity.MethodAccessEntity;
import org.h3t.test.entity.RelatedEntity;
import org.h3t.test.util.HibernateMockFactory;
import org.h3t.test.util.TestLoadServiceFactory;
import org.h3t.util.BeanProperty;
import org.junit.After;
import org.junit.Test;

public class EntityAspectTest  {



	@After
	public void tearDown() throws Exception {
		TestLoadServiceFactory.clearQueue();
	}
	@Test
	public void testField() throws Throwable {
		testField(new OneToOneAspect(), FieldAccessEntity.class
				.getField("oneToOneLazy"), ChildState.LAZY_UNINITIALIZED);
		testField(new ManyToOneAspect(), FieldAccessEntity.class
				.getField("manyToOneLazy"), ChildState.LAZY_UNINITIALIZED);
		testField(new OneToOneAspect(), FieldAccessEntity.class
				.getField("oneToOneLazy"), ChildState.LAZY_INITIALIZED);
		testField(new ManyToOneAspect(), FieldAccessEntity.class
				.getField("manyToOneLazy"), ChildState.LAZY_INITIALIZED);
		testField(new OneToOneAspect(), FieldAccessEntity.class
				.getField("oneToOneLazy"), ChildState.EAGER);
		testField(new ManyToOneAspect(), FieldAccessEntity.class
				.getField("manyToOneLazy"), ChildState.EAGER);
	}
	@Test
	public void testMethod() throws Throwable {
		testMethod(new OneToOneAspect(), MethodAccessEntity.class.getMethod(
				"getOneToOneLazy", new Class[0]), ChildState.LAZY_UNINITIALIZED);
		testMethod(new ManyToOneAspect(), MethodAccessEntity.class.getMethod(
				"getManyToOneLazy", new Class[0]),
				ChildState.LAZY_UNINITIALIZED);
		testMethod(new OneToOneAspect(), MethodAccessEntity.class.getMethod(
				"getOneToOneLazy", new Class[0]), ChildState.LAZY_INITIALIZED);
		testMethod(new ManyToOneAspect(), MethodAccessEntity.class.getMethod(
				"getManyToOneLazy", new Class[0]), ChildState.LAZY_INITIALIZED);
		testMethod(new OneToOneAspect(), MethodAccessEntity.class.getMethod(
				"getOneToOneLazy", new Class[0]), ChildState.EAGER);
		testMethod(new ManyToOneAspect(), MethodAccessEntity.class.getMethod(
				"getManyToOneLazy", new Class[0]), ChildState.EAGER);
	}
	
	private static void testField(EntityAspect aspect, Field field,
			ChildState state) throws Throwable {
		String loadedValue = "loaded";
		TestLoadServiceFactory.queueResult(new RelatedEntity(loadedValue));
		FieldAccessEntity entity = new FieldAccessEntity();
		RelatedEntity originalValue;
		if (state.equals(ChildState.EAGER)) {
			originalValue = new RelatedEntity("original");
		} else {
			boolean initialized = state.equals(ChildState.LAZY_INITIALIZED) ? true
					: false;
			originalValue = HibernateMockFactory.newHibernateProxy(
					RelatedEntity.class, initialized);
		}
		field.set(entity, originalValue);
		ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
		FieldSignature signature = mock(FieldSignature.class);
		when(signature.getField()).thenReturn(field);
		when(joinPoint.getSignature()).thenReturn(signature);
		when(joinPoint.getTarget()).thenReturn(entity);
		when(joinPoint.proceed()).thenReturn(originalValue);
		RelatedEntity returned = (RelatedEntity) aspect.getField(joinPoint);
		if (state.equals(ChildState.LAZY_UNINITIALIZED)) {
			assertEquals(loadedValue, returned.s());
		} else {
			assertTrue(originalValue == returned);
		}
	}

	private static void testMethod(EntityAspect aspect, Method method,
			ChildState state) throws Throwable {
		String loadedValue = "loaded";
		TestLoadServiceFactory.queueResult(new RelatedEntity(loadedValue));
		MethodAccessEntity entity = new MethodAccessEntity();
		RelatedEntity originalValue;
		if (state.equals(ChildState.EAGER)) {
			originalValue = new RelatedEntity("original");
		} else {
			boolean initialized = state.equals(ChildState.LAZY_INITIALIZED) ? true
					: false;
			originalValue = HibernateMockFactory.newHibernateProxy(
					RelatedEntity.class, initialized);
		}
		new BeanProperty<RelatedEntity>(method).set(entity, originalValue);
		ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
		MethodSignature signature = mock(MethodSignature.class);
		when(signature.getMethod()).thenReturn(method);
		when(joinPoint.getSignature()).thenReturn(signature);
		when(joinPoint.getTarget()).thenReturn(entity);
		when(joinPoint.proceed()).thenReturn(originalValue);
		RelatedEntity returned = (RelatedEntity) aspect.invokeMethod(joinPoint);
		if (state.equals(ChildState.LAZY_UNINITIALIZED)) {
			assertEquals(loadedValue, returned.s());
		} else {
			assertTrue(originalValue == returned);
		}
	}

}
