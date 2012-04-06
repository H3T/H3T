package org.h3t.aspect;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class SerializationInterceptor implements MethodInterceptor {
	
	private Object replacement;
	
	public SerializationInterceptor(Object replacement){
		this.replacement = replacement;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return replacement;
	}

}
