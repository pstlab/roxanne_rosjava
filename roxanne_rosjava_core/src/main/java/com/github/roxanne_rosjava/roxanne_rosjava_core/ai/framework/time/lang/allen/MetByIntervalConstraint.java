package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.allen;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.TemporalInterval;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.BinaryTemporalConstraint;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.TemporalConstraintType;

/**
 * 
 * @author alessandroumbrico
 *
 */
public final class MetByIntervalConstraint extends BinaryTemporalConstraint<TemporalInterval, TemporalInterval> 
{
	private long lb;
	private long ub;
	
	/**
	 * 
	 */
	protected MetByIntervalConstraint() {
		super(TemporalConstraintType.MET_BY);
		this.lb = 0;
		this.ub = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getLowerBound() {
		return lb;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getUpperBound() {
		return ub;
	}
}
