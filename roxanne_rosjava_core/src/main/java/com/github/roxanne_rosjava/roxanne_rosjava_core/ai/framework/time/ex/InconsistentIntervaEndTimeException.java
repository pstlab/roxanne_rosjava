package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.ex;

/**
 * 
 * @author anacleto
 *
 */
public class InconsistentIntervaEndTimeException extends TemporalIntervalCreationException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public InconsistentIntervaEndTimeException(String msg) {
		super(msg);
	}
}
