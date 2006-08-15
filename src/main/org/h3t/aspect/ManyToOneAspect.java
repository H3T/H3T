package org.h3t.aspect;

import java.lang.reflect.AnnotatedElement;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class ManyToOneAspect extends EntityAspect{

	public boolean isLazy(AnnotatedElement element) {
		return element.getAnnotation(ManyToOne.class).fetch().equals(FetchType.LAZY);
	}


}
