package org.h3t.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AllTests {
	public static Test suite(){
		TestSuite suite = new TestSuite();
		suite.addTestSuite(CollectionAspectTest.class);
		suite.addTestSuite(EntityAspectTest.class);
		suite.addTestSuite(EntityCallbackFilterTest.class);
		suite.addTestSuite(LazyAspectTest.class);
		suite.addTestSuite(LazyLoaderTest.class);
		suite.addTestSuite(LoaderTest.class);
		suite.addTestSuite(PropertyTest.class);
		return suite;
	}
	
	public static void main(String[] args){
		TestRunner.run(suite());
	}

}
