package org.h3t.test;

import org.h3t.aspect.EntityCallbackFilter;
import org.h3t.aspect.EntitySerializer;

import junit.framework.TestCase;

public class EntityCallbackFilterTest extends TestCase {
	public void testEntitySerializer() throws Exception {
		EntityCallbackFilter filter = new EntityCallbackFilter();
		assertEquals(1, filter.accept(EntitySerializer.class.getMethods()[0]));

	}

	public void testOther() throws Exception {
		EntityCallbackFilter filter = new EntityCallbackFilter();
		assertEquals(0, filter.accept(Object.class.getMethods()[0]));

	}

}
