package org.h3t.aspect;

import java.lang.reflect.AnnotatedElement;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

public class OneToOneAspect extends EntityAspect{

	public boolean isLazy(AnnotatedElement element) {
		return element.getAnnotation(OneToOne.class).fetch().equals(FetchType.LAZY);
	}


}
