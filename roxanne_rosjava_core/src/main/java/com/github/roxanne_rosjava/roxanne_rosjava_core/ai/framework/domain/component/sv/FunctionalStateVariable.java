package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.sv;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.framework.DomainComponentConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.DomainComponentType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ResolverType;

/**
 * 
 * @author anacleto
 *
 */
public class FunctionalStateVariable extends StateVariable 
{
	/**
	 * 
	 * @param name
	 */
	@DomainComponentConfiguration(resolvers = {
			// planning resolver
			ResolverType.PLAN_REFINEMENT
	})
	protected FunctionalStateVariable(String name) {
		super(name, DomainComponentType.SV_FUNCTIONAL);
	}
}
