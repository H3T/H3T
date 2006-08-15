package org.h3t.aspect;

import java.lang.reflect.AnnotatedElement;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

public class ManyToManyAspect extends CollectionAspect {

	public boolean isLazy(AnnotatedElement element) {
		return element.getAnnotation(ManyToMany.class).fetch().equals(
				FetchType.LAZY);
	}

}
