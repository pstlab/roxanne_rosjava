package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.ex;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.ConstraintPropagationException;

/**
 * 
 * @author anacleto
 *
 */
public class ParameterConstraintPropagationException extends ConstraintPropagationException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public ParameterConstraintPropagationException(String msg) {
		super(msg);
	}
}
