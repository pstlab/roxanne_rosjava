package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.failure;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNode;

/**
 * 
 * @author alessandroumbrico
 *
 */
public class PlanRepairInformation 
{
	private ExecutionNode node;
	private long duration;
	
	/**
	 * 
	 * @param node
	 * @param duration
	 */
	protected PlanRepairInformation(ExecutionNode node, long duration) {
		this.node = node;
		this.duration = duration;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public ExecutionNode getNode() {
		return node;
	}
}
