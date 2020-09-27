package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query;

/**
 * A QueryManager is an element responsible for handling and processing
 * a specific type of queries
 * 
 * @author anacleto
 *
 */
public interface QueryManager <T extends Query> {

	/**
	 * Generic method to handle a generic type of query
	 * 
	 * @param query
	 */
	public void process(T query);
}
