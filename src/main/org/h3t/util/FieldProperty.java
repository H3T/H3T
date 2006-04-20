package org.h3t.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldProperty<T> implements Property<T> {
	
	private Field field;
	
	public FieldProperty(Field field){
		this.field = field;
	}

	public void set(Object obj, T value) {
		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public T get(Object obj) {
		try {
			field.setAccessible(true);
			return (T) field.get(obj);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		return field.getAnnotation(annotationType);
	}

}
