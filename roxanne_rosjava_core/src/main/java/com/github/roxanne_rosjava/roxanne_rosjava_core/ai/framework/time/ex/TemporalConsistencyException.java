package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.ex;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.ConsistencyCheckException;

/**
 * 
 * @author anacleto
 *
 */
public class TemporalConsistencyException extends ConsistencyCheckException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public TemporalConsistencyException(String msg) {
		super(msg);
	}
}
