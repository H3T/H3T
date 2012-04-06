package org.h3t.aspect;

import java.lang.reflect.AnnotatedElement;

public interface LazyAspect {
	boolean isLazy(AnnotatedElement element);

}
