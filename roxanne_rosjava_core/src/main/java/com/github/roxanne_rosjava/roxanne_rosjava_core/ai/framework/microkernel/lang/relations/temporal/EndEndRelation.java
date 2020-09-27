package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.temporal;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.Decision;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.RelationType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.TemporalConstraintFactory;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.TemporalConstraintType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.TimePointDistanceConstraint;

/**
 * 
 * @author anacleto
 *
 */
public class EndEndRelation extends TemporalRelation 
{
	private long[] bound;
	
	private TemporalConstraintFactory factory;
	
	/**
	 * 
	 * @param reference
	 * @param target
	 */
	protected EndEndRelation(int id, Decision reference, Decision target) {
		super(id, RelationType.END_END, reference, target);
		this.bound = new long[] {0, Long.MAX_VALUE - 1};
		// get factory
		this.factory = new TemporalConstraintFactory();
	}
	
	/**
	 * 
	 */
	@Override
	public long[][] getBounds() {
		return new long[][] {this.bound};
	}
	
	/**
	 * 
	 */
	@Override
	public void setBounds(long[][] bounds) {
		this.bound = bounds[0];
	}
	
	/**
	 * 
	 * @param bound
	 */
	public void setBound(long[] bound) {
		this.bound = bound;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getLowerBound() {
		return this.bound[0];
	}
	
	/**
	 * 
	 */
	public long getUpperBound() {
		return this.bound[1];
	}

	/**
	 * 
	 * @return
	 */
	public TemporalConstraintType getConstraintType() {
		return TemporalConstraintType.TIME_POINT_DISTANCE;
	}
	
	/**
	 * 
	 */
	@Override
	public TimePointDistanceConstraint create() {
		// create constraint
		TimePointDistanceConstraint c = this.factory.create(
				TemporalConstraintType.TIME_POINT_DISTANCE);

		// set intervals
		c.setReference(this.reference.getToken().getInterval().getEndTime());
		c.setTarget(this.target.getToken().getInterval().getEndTime());
		// set bounds
		c.setDistanceLowerBound(this.bound[0]);
		c.setDistanceUpperBound(this.bound[1]);
		// set constraint
		this.constraint = c;
		// get constraint
		return c;
	}
}
