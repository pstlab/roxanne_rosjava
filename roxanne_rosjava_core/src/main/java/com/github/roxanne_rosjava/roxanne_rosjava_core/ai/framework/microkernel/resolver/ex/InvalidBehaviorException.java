package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ex;

/**
 * 
 * @author anacleto
 *
 */
public class InvalidBehaviorException extends UnsolvableFlawException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public InvalidBehaviorException(String msg) {
		super(msg);
	}
}
