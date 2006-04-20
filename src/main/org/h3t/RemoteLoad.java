package org.h3t;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.h3t.util.JndiLoadServiceFactory;
/**
 * @author Rob Worsnop
 */
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface RemoteLoad {
	/**
	 * A custom implementation of <code>LoadServiceFactory</code>.
	 */
	Class<? extends LoadServiceFactory> factory() default JndiLoadServiceFactory.class;
}
