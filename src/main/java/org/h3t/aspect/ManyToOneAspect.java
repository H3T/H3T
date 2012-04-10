package org.h3t.aspect;

import java.lang.reflect.AnnotatedElement;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public class ManyToOneAspect extends EntityAspect{

	public boolean isLazy(AnnotatedElement element) {
		return element.getAnnotation(ManyToOne.class).fetch().equals(FetchType.LAZY);
	}

	@Override
	@Around("get(* *->@javax.persistence.ManyToOne) AND get(* *->@org.h3t.RemoteLoad)")
	public Object getField(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.getField(joinPoint);
	}

	@Override
	@Around("execution(* *->@javax.persistence.ManyToOne()) AND execution(* *->@org.h3t.RemoteLoad())")
	public Object invokeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.invokeMethod(joinPoint);
	}


}
