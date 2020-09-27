package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.csp.solver;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.FrameworkObject;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.csp.event.ParameterNotification;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.csp.event.ParameterNotificationObserver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.lang.Parameter;

/**
 * 
 * @author anacleto
 *
 */
public abstract class ParameterSolver extends FrameworkObject implements ParameterNotificationObserver 
{
	/**
	 * 
	 * @param type
	 */
	protected ParameterSolver() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isConsistent();
	
	/**
	 * 
	 */
	public abstract void computeSolution();
	
	/**
	 * 
	 * @param param
	 */
	public abstract void computeValues(Parameter<?> param);
	
	/**
	 * 
	 */
	@Override
	public abstract void update(ParameterNotification info);
}
