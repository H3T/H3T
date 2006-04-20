package org.h3t.util;

import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

import org.h3t.H3TException;
import org.h3t.LoadServiceFactory;
import org.h3t.RemoteLoad;

public class LoadServiceFactoryMap {
	private static Map<Class, LoadServiceFactory> factories = new HashMap<Class, LoadServiceFactory>();
	
	public static LoadServiceFactory getFactory(AnnotatedElement element){
		return getFactory(element.getAnnotation(RemoteLoad.class).factory());
	}
	
	public static synchronized LoadServiceFactory getFactory(Class<? extends LoadServiceFactory> clazz){
		try {
			LoadServiceFactory ret = factories.get(clazz);
			if (ret == null){
				ret = clazz.newInstance();
			}
			return ret;
		} catch (InstantiationException e) {
			throw new H3TException(e);
		} catch (IllegalAccessException e) {
			throw new H3TException(e);
		}
	}
	


}
