package org.h3t.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.h3t.aspect.ManyToManyAspect;
import org.h3t.aspect.ManyToOneAspect;
import org.h3t.aspect.OneToManyAspect;
import org.h3t.aspect.OneToOneAspect;
import org.h3t.test.entity.FieldAccessEntity;
import org.h3t.test.entity.MethodAccessEntity;
import org.junit.Test;

public class LazyAspectTest  {
	
	@Test
	public void testLazy() throws Exception{
		 assertTrue(new OneToOneAspect().isLazy(FieldAccessEntity.class.getField("oneToOneLazy")));
		 assertTrue(new ManyToOneAspect().isLazy(FieldAccessEntity.class.getField("manyToOneLazy")));
		 assertTrue(new OneToManyAspect().isLazy(FieldAccessEntity.class.getField("oneToManyLazy")));
		 assertTrue(new ManyToManyAspect().isLazy(FieldAccessEntity.class.getField("manyToManyLazy")));
		 assertTrue(new OneToOneAspect().isLazy(MethodAccessEntity.class.getMethod("getOneToOneLazy")));
		 assertTrue(new ManyToOneAspect().isLazy(MethodAccessEntity.class.getMethod("getManyToOneLazy")));
		 assertTrue(new OneToManyAspect().isLazy(MethodAccessEntity.class.getMethod("getOneToManyLazy")));
		 assertTrue(new ManyToManyAspect().isLazy(MethodAccessEntity.class.getMethod("getManyToManyLazy")));
	}
	
	@Test
	public void testEager() throws Exception{
		 assertFalse(new OneToOneAspect().isLazy(FieldAccessEntity.class.getField("oneToOneEager")));
		 assertFalse(new ManyToOneAspect().isLazy(FieldAccessEntity.class.getField("manyToOneEager")));
		 assertFalse(new OneToManyAspect().isLazy(FieldAccessEntity.class.getField("oneToManyEager")));
		 assertFalse(new ManyToManyAspect().isLazy(FieldAccessEntity.class.getField("manyToManyEager")));
		 assertFalse(new OneToOneAspect().isLazy(MethodAccessEntity.class.getMethod("getOneToOneEager")));
		 assertFalse(new ManyToOneAspect().isLazy(MethodAccessEntity.class.getMethod("getManyToOneEager")));
		 assertFalse(new OneToManyAspect().isLazy(MethodAccessEntity.class.getMethod("getOneToManyEager")));
		 assertFalse(new ManyToManyAspect().isLazy(MethodAccessEntity.class.getMethod("getManyToManyEager")));
	}
	

	

	

}
