package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.ex;

/**
 * 
 * @author anacleto
 *
 */
public class TransitionNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public TransitionNotFoundException(String msg) {
		super(msg);
	}
}