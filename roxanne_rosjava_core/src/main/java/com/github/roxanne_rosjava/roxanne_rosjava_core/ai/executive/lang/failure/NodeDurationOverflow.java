package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.failure;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNode;

/**
 * 
 * @author anacleto
 *
 */
public class NodeDurationOverflow extends ExecutionFailureCause 
{
	private long observedDuration;
	
	/**
	 * 
	 * @param tick
	 * @param node
	 * @param observedDuration
	 */
	public NodeDurationOverflow(long tick, ExecutionNode node, long observedDuration) {
		super(tick, ExecutionFailureCauseType.NODE_DURATION_OVERFLOW, node);
		this.observedDuration = observedDuration;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getObservedDuration() {
		return observedDuration;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "[DurationOverflow] The observed duration exceeds the upper bound of the domain specification\n"
				+ "\t- observed-duration= " + this.observedDuration + "\n";
	}
}
