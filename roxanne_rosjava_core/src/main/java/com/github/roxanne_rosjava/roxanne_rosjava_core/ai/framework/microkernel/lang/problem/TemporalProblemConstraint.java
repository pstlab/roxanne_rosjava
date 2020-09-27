package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.problem;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.RelationType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.ConstraintCategory;

/**
 * 
 * @author anacleto
 *
 */
public class TemporalProblemConstraint extends ProblemConstraint 
{
	private long[][] bounds;
	
	/**
	 * 
	 * @param type
	 * @param reference
	 * @param target
	 * @param bounds
	 */
	protected TemporalProblemConstraint(RelationType type, ProblemFluent reference, ProblemFluent target, long[][] bounds) {
		super(type, reference, target);
		// check type
		if (type.getCategory().equals(ConstraintCategory.PARAMETER_CONSTRAINT)) {
			throw new RuntimeException("Invalid relation type for temporal constraints " + type);
		}
		// set temporal bounds
		this.bounds = bounds;
	}
	
	/**
	 * 
	 * @return
	 */
	public long[][] getBounds() {
		return bounds;
	}
}
