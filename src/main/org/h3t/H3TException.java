package org.h3t;

@SuppressWarnings("serial")
/**
 * @author Rob Worsnop
 */
public class H3TException extends RuntimeException {


	public H3TException(String message) {
		super(message);
	}

	public H3TException(String message, Throwable cause) {
		super(message, cause);
	}

	public H3TException(Throwable cause) {
		super(cause);
	}

}
