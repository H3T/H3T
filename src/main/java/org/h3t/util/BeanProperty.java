package org.h3t.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanProperty<T> implements Property<T> {

	private Method getter;

	private Method setter;

	public BeanProperty(String propertyName, Class clazz) {
		try {
			getter = clazz.getMethod("get" + capitalizeFirstChar(propertyName));
			setter = clazz.getMethod("set" + capitalizeFirstChar(propertyName),
					new Class[] { getter.getReturnType() });
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public BeanProperty(Method getter) {
		try {
			this.getter = getter;
			this.setter = getter.getDeclaringClass().getMethod(
					"set" + getter.getName().substring(3),
					new Class[] { getter.getReturnType() });
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public void set(Object obj, T value) {
		try {
			setter.invoke(obj, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	public T get(Object obj) {
		try {
			return (T) getter.invoke(obj);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		return getter.getAnnotation(annotationType);
	}

	private static String capitalizeFirstChar(String s) {
		StringBuilder ret = new StringBuilder(s);
		Character ch = new Character(Character.toUpperCase(ret.charAt(0)));
		ret.replace(0, 1, ch.toString());
		return ret.toString();
	}

}
