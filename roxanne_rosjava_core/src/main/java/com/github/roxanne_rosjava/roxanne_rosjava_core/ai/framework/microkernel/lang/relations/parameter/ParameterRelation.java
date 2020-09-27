package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.parameter;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.Decision;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.Relation;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.RelationType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.lang.constraints.ParameterConstraint;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.lang.constraints.ParameterConstraintType;

/**
 * 
 * @author anacleto
 *
 */
public abstract class ParameterRelation extends Relation 
{
	protected String referenceParameterLabel;
	
	/**
	 * 
	 * @param id
	 * @param type
	 * @param reference
	 * @param target
	 */
	protected ParameterRelation(int id, RelationType type, Decision reference, Decision target) {
		super(id, type, reference, target);
	}
	
	/**
	 * 
	 * @param label
	 */
	public void setReferenceParameterLabel(String label) {
		this.referenceParameterLabel = label;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getReferenceParameterLabel() {
		return this.referenceParameterLabel;
	}
	
	/**
	 * 
	 */
	@Override
	public ParameterConstraint getConstraint() {
		// cast constraint
		return (ParameterConstraint) this.constraint;
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract ParameterConstraintType getConstraintType();
	
	/**
	 * 
	 */
	@Override
	public abstract ParameterConstraint create();
	
}
