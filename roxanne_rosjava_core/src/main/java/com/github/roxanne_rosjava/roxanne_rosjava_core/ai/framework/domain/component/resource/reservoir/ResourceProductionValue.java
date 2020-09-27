package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.resource.reservoir;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.ComponentValueType;

/**
 * 
 * @author anacleto
 *
 */
public class ResourceProductionValue extends ResourceUsageValue
{
	/**
	 * 
	 * @param label
	 * @param duration
	 * @param controllable
	 * @param resource
	 */
	protected ResourceProductionValue(String label, long[] duration, boolean controllable, ReservoirResource resource) {
		super(ComponentValueType.RESOURCE_PRODUCTION, label,  duration, controllable, resource);
	}
}