package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.TemporalData;

/**
 * 
 * @author anacleto
 *
 */
public abstract class UnaryTemporalConstraint<R extends TemporalData> extends TemporalConstraint 
{
	protected R reference;
	
	/**
	 * 
	 * @param type
	 */
	protected UnaryTemporalConstraint(TemporalConstraintType type) {
		super(type);
	}
	
	/**
	 * 
	 * @return
	 */
	public R getReference() {
		return reference;
	}
	
	/**
	 * 
	 * @param reference
	 */
	public void setReference(R reference) {
		this.reference = reference;
	}
}
