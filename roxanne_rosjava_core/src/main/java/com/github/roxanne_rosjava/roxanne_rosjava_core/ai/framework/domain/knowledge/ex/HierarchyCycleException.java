package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.knowledge.ex;

/**
 * 
 * @author anacleto
 *
 */
public class HierarchyCycleException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public HierarchyCycleException(String msg) {
		super(msg);
	}
}
