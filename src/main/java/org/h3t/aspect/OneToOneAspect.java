package org.h3t.aspect;

import java.lang.reflect.AnnotatedElement;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public class OneToOneAspect extends EntityAspect{

	public boolean isLazy(AnnotatedElement element) {
		return element.getAnnotation(OneToOne.class).fetch().equals(FetchType.LAZY);
	}

	@Override
	@Around("get(* *->@javax.persistence.OneToOne) AND get(* *->@org.h3t.RemoteLoad)")
	public Object getField(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.getField(joinPoint);
	}

	@Override
	@Around("execution(* *->@javax.persistence.OneToOne()) AND execution(* *->@org.h3t.RemoteLoad())")
	public Object invokeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.invokeMethod(joinPoint);
	}
}
