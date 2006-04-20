package org.h3t.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.h3t.H3TException;
import org.h3t.LoadService;
import org.h3t.LoadServiceFactory;

public class JndiLoadServiceFactory implements LoadServiceFactory {

	public LoadService lookup() throws H3TException {
		try {
			Context ctx = new InitialContext();
			return (LoadService)ctx.lookup(LoadService.class.getName());
		} catch (NamingException e) {
			throw new H3TException(e);
		}
	}

}
