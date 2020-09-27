package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.ex;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.ConsistencyCheckException;

/**
 * 
 * @author anacleto
 *
 */
public class ParameterConsistencyException extends ConsistencyCheckException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public ParameterConsistencyException(String msg) {
		super(msg);
	}
}
