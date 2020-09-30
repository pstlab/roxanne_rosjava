package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.resource.reservoir;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.Decision;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.resource.ResourceEvent;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.resource.ResourceEventType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.TimePoint;

/**
 * 
 * @author anacleto
 *
 */
public class ConsumptionResourceEvent extends ResourceEvent<TimePoint>
{
	/**
	 * 
	 * @param activity
	 * @param amount
	 */
	protected ConsumptionResourceEvent(Decision activity, int amount) {
		super(ResourceEventType.CONSUMPTION, activity, amount, activity.getToken().getInterval().getStartTime());
	}
}