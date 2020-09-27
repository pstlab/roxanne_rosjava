package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.ex;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.ConsistencyCheckException;

/**
 * 
 * @author anacleto
 *
 */
public class PseudoControllabilityException extends ConsistencyCheckException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public PseudoControllabilityException(String msg) {
		super(msg);
	}
}
