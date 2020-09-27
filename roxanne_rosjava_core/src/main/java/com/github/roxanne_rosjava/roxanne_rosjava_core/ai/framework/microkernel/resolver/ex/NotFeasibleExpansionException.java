package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ex;

/**
 * 
 * @author anacleto
 *
 */
public class NotFeasibleExpansionException extends UnsolvableFlawException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public NotFeasibleExpansionException(String msg) {
		super(msg);
	}
}
