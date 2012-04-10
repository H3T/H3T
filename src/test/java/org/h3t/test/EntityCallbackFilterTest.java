package org.h3t.test;

import static org.junit.Assert.assertEquals;

import org.h3t.aspect.EntityCallbackFilter;
import org.h3t.aspect.EntitySerializer;
import org.junit.Test;

public class EntityCallbackFilterTest {
	@Test
	public void testEntitySerializer() throws Exception {
		EntityCallbackFilter filter = new EntityCallbackFilter();
		assertEquals(1, filter.accept(EntitySerializer.class.getMethods()[0]));

	}
	@Test
	public void testOther() throws Exception {
		EntityCallbackFilter filter = new EntityCallbackFilter();
		assertEquals(0, filter.accept(Object.class.getMethods()[0]));

	}

}
