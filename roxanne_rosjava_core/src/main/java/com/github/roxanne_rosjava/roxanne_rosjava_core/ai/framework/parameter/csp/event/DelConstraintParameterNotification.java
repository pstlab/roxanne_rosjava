package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.csp.event;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.lang.constraints.ParameterConstraint;

/**
 * 
 * @author anacleto
 *
 */
public class DelConstraintParameterNotification extends ParameterNotification 
{
	private ParameterConstraint constraint;
	
	/**
	 * 
	 */
	protected DelConstraintParameterNotification() {
		super(ParameterNotificationType.DEL_CONSTRAINT);
	}
	
	/**
	 * 
	 * @param constraint
	 */
	public void setConstraint(ParameterConstraint constraint) {
		this.constraint = constraint;
	}
	
	/**
	 * 
	 * @return
	 */
	public ParameterConstraint getParameterConstraint() {
		return this.constraint;
	}
}
