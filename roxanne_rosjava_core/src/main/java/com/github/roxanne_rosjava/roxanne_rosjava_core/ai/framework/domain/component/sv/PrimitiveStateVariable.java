package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.sv;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.framework.DomainComponentConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.DomainComponentType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ResolverType;

/**
 * 
 */
public final class PrimitiveStateVariable extends StateVariable
{
	/**
	 * 
	 * @param name
	 */
	@DomainComponentConfiguration(resolvers = {
			// planning resolver
			ResolverType.PLAN_REFINEMENT,
			// scheduling resolver
			ResolverType.TIMELINE_SCHEDULING_RESOLVER,
			// time-line gap resolver
			ResolverType.TIMELINE_BEHAVIOR_PLANNING_RESOLVER,
	})
	protected PrimitiveStateVariable(String name) {
		super(name, DomainComponentType.SV_PRIMITIVE);
	}
}
