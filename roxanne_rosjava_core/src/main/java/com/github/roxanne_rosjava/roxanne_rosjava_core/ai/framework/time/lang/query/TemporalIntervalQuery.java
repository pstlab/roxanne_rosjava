package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.query;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query.TemporalQuery;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query.TemporalQueryType;

/**
 * 
 * @author anacleto
 *
 */
public abstract class TemporalIntervalQuery extends TemporalQuery {

	/**
	 * 
	 * @param type
	 */
	protected TemporalIntervalQuery(TemporalQueryType type) {
		super(type);
	}
}
