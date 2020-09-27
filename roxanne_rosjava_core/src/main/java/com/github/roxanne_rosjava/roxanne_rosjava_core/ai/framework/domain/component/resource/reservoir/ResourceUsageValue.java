package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.resource.reservoir;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.ComponentValue;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.ComponentValueType;

/**
 * 
 * @author anacleto
 *
 */
public abstract class ResourceUsageValue extends ComponentValue 
{
	/**
	 * 
	 * @param type
	 * @param label
	 * @param duration
	 * @param controllable
	 * @param component
	 */
	protected ResourceUsageValue(ComponentValueType type, String label, long[] duration, boolean controllable, ReservoirResource component) {
		super(label, type, duration, controllable, component);
	}
}

