package org.h3t.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.h3t.Loader;
import org.h3t.test.entity.A;
import org.h3t.test.entity.B;
import org.h3t.test.entity.C;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoaderTest {
	private final static String TEST_VALUE = "test value";


	private SessionFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new Configuration()
			.addAnnotatedClass(A.class)
			.addAnnotatedClass(B.class)
			.addAnnotatedClass(C.class)
			.setProperty("hibernate.show_sql","false")
			.setProperty("hibernate.jdbc.batch_size", "0")
			.setProperty("hibernate.connection.driver_class", "org.h2.Driver")
			.setProperty("hibernate.connection.url", "jdbc:h2:mem:orders")
			.setProperty("hibernate.hbm2ddl.auto", "create-drop")
			.setProperty("hibernate.dialect", H2Dialect.class.getName())
			.buildSessionFactory();
	}
	@After
	public void tearDown() throws Exception {
		factory.close();
	}
	@Test
	public void testEntityField() throws Exception {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		A a = new A();
		a.b = new B(TEST_VALUE);
		session.persist(a);
		transaction.commit();
		session.close();

		session = factory.openSession();
		a = (A) session.get(A.class, a.id);
		session.close();
		
		session = factory.openSession();
		try {
			a.b.getS();
			fail("Expecting exception");
		} catch (LazyInitializationException e) {
		}
		B b = (B) Loader
				.loadAssociatedEntity(session, a, A.class.getField("b"));
		session.close();
		assertEquals(TEST_VALUE, b.getS());
	}
	
	public void testEntityMethod() throws Exception {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		C c = new C();
		c.setB(new B(TEST_VALUE));
		session.persist(c);
		transaction.commit();
		session.close();

		session = factory.openSession();
		c = (C) session.get(C.class, c.getId());
		session.close();
		
		session = factory.openSession();
		try {
			c.getB().getS();
			fail("Expecting exception");
		} catch (LazyInitializationException e) {
		}
		B b = (B) Loader
				.loadAssociatedEntity(session, c,  C.class.getMethod("getB", new Class[0]));
		session.close();
		assertEquals(TEST_VALUE, b.getS());
	}
	
	@SuppressWarnings("unchecked")
	public void testCollectionField() throws Exception{
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		A a = new A();
		a.bs.add(new B(TEST_VALUE));
		session.persist(a);
		transaction.commit();
		session.close();
		
		session = factory.openSession();
		a = (A) session.get(A.class, a.id);
		session.close();
		
		session = factory.openSession();
		try {
			a.bs.iterator();
			fail("Expecting exception");
		} catch (LazyInitializationException e) {
		}
		List<B> bs = (List<B>)Loader.loadAssociatedCollection(session, a, A.class.getField("bs"));
		session.close();
		assertEquals(TEST_VALUE, bs.get(0).getS());
	}

	@SuppressWarnings("unchecked")
	public void testCollectionMethod() throws Exception{
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		C c = new C();
		c.getBs().add(new B(TEST_VALUE));
		session.persist(c);
		transaction.commit();
		session.close();
		
		session = factory.openSession();
		c = (C) session.get(C.class, c.getId());
		session.close();
		
		session = factory.openSession();
		try {
			c.getBs().iterator();
			fail("Expecting exception");
		} catch (LazyInitializationException e) {
		}
		List<B> bs = (List<B>)Loader.loadAssociatedCollection(session, c, C.class.getMethod("getBs", new Class[0]));
		session.close();
		assertEquals(TEST_VALUE, bs.get(0).getS());
	}

}
