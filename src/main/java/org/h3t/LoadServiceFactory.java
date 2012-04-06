package org.h3t;
/**
 * <p>The developer implements this interface to provide a way for
 * the framework to acquire a {@link org.h3t.LoadService}.
 * The class name of an implementation of this interface can
 * be specified as the <code>factory</code> parameter in <code>RemoteLoad</code> annotations. 
 * 
 * @author Rob Worsnop
 * @see org.h3t.LoadService 
 * @see org.h3t.RemoteLoad
 *
 */
public interface LoadServiceFactory {
	/**
	 * Acquires the <code>LoadService</code> for retrieving
	 * lazy-loaded entities from the server.
	 * @return the service.
	 * @throws H3TException
	 */
	LoadService lookup() throws H3TException;

}
