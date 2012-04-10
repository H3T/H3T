package org.h3t.aspect;

import java.lang.reflect.AnnotatedElement;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public class OneToManyAspect extends CollectionAspect {

	public boolean isLazy(AnnotatedElement element) {
		return element.getAnnotation(OneToMany.class).fetch().equals(
				FetchType.LAZY);
	}
	@Override
	@Around("get(* *->@javax.persistence.OneToMany) AND get(* *->@org.h3t.RemoteLoad)")
	public Object proxyField(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.proxyField(joinPoint);
	}

	@Override
	@Around("execution(* *->@javax.persistence.OneToMany()) AND execution(* *->@org.h3t.RemoteLoad())")
	public Object proxyProperty(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.proxyProperty(joinPoint);
	}

}
