package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ex;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.failure.ExecutionFailureCause;

/**
 * 
 * @author anacleto
 *
 */
public class NodeDispatchingException extends ExecutionException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public NodeDispatchingException(String msg, ExecutionFailureCause cause) {
		super(msg, cause);
	}
}
