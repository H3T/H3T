package org.h3t.util;

import java.lang.annotation.Annotation;


public interface Property<T>{
	
	void set(Object obj, T value);
	T get(Object obj);
	<T extends Annotation> T getAnnotation(Class<T> annotationType);

}
