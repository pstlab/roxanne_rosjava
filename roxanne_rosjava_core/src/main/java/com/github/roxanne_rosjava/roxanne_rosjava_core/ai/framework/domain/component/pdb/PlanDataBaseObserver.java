package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.pdb;

/**
 * 
 * @author anacleto
 *
 */
public interface PlanDataBaseObserver 
{
	/**
	 * 
	 * @param solution
	 */
	public void notify(PlanDataBaseEvent event);
}
