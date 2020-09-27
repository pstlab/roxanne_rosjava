package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ex;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.failure.ExecutionFailureCause;

/**
 * 
 * @author anacleto
 *
 */
public class NodeObservationException extends ExecutionException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param cause
	 */
	public NodeObservationException(String msg, ExecutionFailureCause cause) {
		super(msg, cause);
	}
}
