package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.lang.query;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query.TemporalQuery;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query.TemporalQueryType;

/**
 * 
 * @author anacleto
 *
 */
public abstract class TimePointQuery extends TemporalQuery {

	/**
	 * 
	 * @param type
	 */
	protected TimePointQuery(TemporalQueryType type) {
		super(type);
	}
}
