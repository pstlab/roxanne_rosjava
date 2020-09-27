package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.temporal;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.Decision;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.Relation;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.RelationType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.TemporalConstraint;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.lang.TemporalConstraintType;

/**
 * 
 * @author anacleto
 *
 */
public abstract class TemporalRelation extends Relation 
{
	/**
	 * 
	 * @param id
	 * @param type
	 * @param reference
	 * @param target
	 */
	protected TemporalRelation(int id, RelationType type, Decision reference, Decision target) {
		super(id, type, reference, target);
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract long[][] getBounds();
	
	/**
	 * 
	 */
	public abstract void setBounds(long[][] bounds);
	
	/**
	 * 
	 * @return
	 */
	public abstract TemporalConstraintType getConstraintType();
	
	/**
	 * 
	 */
	@Override
	public abstract TemporalConstraint create();
	
	/**
	 * 
	 */
	@Override
	public TemporalConstraint getConstraint() {
		// cast constraint
		return (TemporalConstraint) this.constraint;
	}
}
