package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.csp.event;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.lang.Parameter;

/**
 * 
 * @author anacleto
 *
 */
public class AddParameterNotification extends ParameterNotification 
{
	private Parameter<?> param;
	
	/**
	 * 
	 */
	protected AddParameterNotification() {
		super(ParameterNotificationType.ADD_PARAM);
	}
	
	/**
	 * 
	 * @param param
	 */
	public void setParameter(Parameter<?> param) {
		this.param = param;
	}
	
	/**
	 * 
	 * @return
	 */
	public Parameter<?> getParameter() {
		return param;
	}
}
