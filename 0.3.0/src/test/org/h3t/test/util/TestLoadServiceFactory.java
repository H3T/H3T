package org.h3t.test.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.h3t.H3TException;
import org.h3t.LoadService;
import org.h3t.LoadServiceFactory;
import org.h3t.util.SerializableField;
import org.h3t.util.SerializableMethod;

public class TestLoadServiceFactory implements LoadServiceFactory {

	private static Queue<Object> queue = new LinkedList<Object>();

	public static void queueResult(Object result) {
		queue.offer(result);
	}
	
	public static void clearQueue(){
		queue.clear();
	}

	public LoadService lookup() throws H3TException {
		return new LoadService() {

			public Collection loadAssociatedCollection(Object entity, SerializableMethod property)  {
				return (Collection)queue.remove();
			}

			public Collection loadAssociatedCollection(Object entity, SerializableField field)  {
				return (Collection)queue.remove();
			}

			public Object loadAssociatedEntity(Object entity, SerializableMethod property)  {
				return queue.remove();
			}

			public Object loadAssociatedEntity(Object entity, SerializableField field)  {
				return queue.remove();
			}
		};
	}

}
