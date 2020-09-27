package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.sv;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.resource.discrete.RequirementResourceEvent;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.Decision;

/**
 * 
 * @author anacleto
 *
 */
public class RequirementStateVariableResourceEvent extends RequirementResourceEvent
{
	/**
	 * 
	 * @param activity
	 */
	protected RequirementStateVariableResourceEvent(Decision activity) {
		super(activity, 1);
	}
}
