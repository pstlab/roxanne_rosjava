package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.resource.reservoir;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.ComponentValueType;

/**
 * 
 * @author anacleto
 *
 */
public class ResourceConsumptionValue extends ResourceUsageValue 
{
	/**
	 * 
	 * @param label
	 * @param duration
	 * @param controllable
	 * @param resource
	 */
	protected ResourceConsumptionValue(String label, long[] duration, boolean controllable, ReservoirResource resource) {
		super(ComponentValueType.RESOURCE_CONSUMPTION, label,  duration, controllable, resource);
	}
}