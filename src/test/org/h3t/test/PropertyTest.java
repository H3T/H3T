package org.h3t.test;

import junit.framework.TestCase;

import org.h3t.util.BeanProperty;
import org.h3t.util.FieldProperty;
import org.h3t.util.Property;

public class PropertyTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testBeanByName() throws Exception {
		testProperty(new BeanProperty<String>("theProperty", TestObject.class));
	}
	
	public void testBeanByMethod() throws Exception {
		testProperty(new BeanProperty<String>(TestObject.class.getDeclaredMethod("getTheProperty", new Class[0])));
	}
	
	public void testField() throws Exception{
		testProperty(new FieldProperty<String>(TestObject.class.getDeclaredField("theProperty")));
	}
	
	public void testProperty(Property<String> prop) throws Exception {
		TestObject obj = new TestObject();
		String s = "";
		prop.set(obj, s);
		assertTrue(s == obj.getTheProperty());
		assertTrue(s == prop.get(obj));
	}
	
	public static class TestObject{
		
		public String theProperty;
		
		public String getTheProperty(){
			return theProperty;
		}
		
		public void setTheProperty(String theProperty){
			this.theProperty = theProperty;
		}
	}
	
	

}
