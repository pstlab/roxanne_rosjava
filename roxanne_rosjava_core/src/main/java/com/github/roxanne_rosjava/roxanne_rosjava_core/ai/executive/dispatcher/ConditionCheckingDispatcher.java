package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.dispatcher;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ex.ExecutionException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ex.NodeObservationException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.failure.ExecutionFailureCause;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.failure.NodeStartOverflow;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.ex.TemporalConstraintPropagationException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.Executive;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNode;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNodeStatus;

/**
 * 
 * @author anacleto
 *
 */
public class ConditionCheckingDispatcher extends Dispatcher<Executive> 
{
	/**
	 * 
	 * @param exec
	 */
	protected ConditionCheckingDispatcher() {
		super();
	}
	
	/**
	 * 
	 */
	@Override
	public final void handleTick(long tick) 
			throws ExecutionException, PlatformException
	{
		// get tau
		long tau = this.executive.convertTickToTau(tick);
		// check human waiting nodes ready to dispatch
		for (ExecutionNode node : this.executive.getNodes(ExecutionNodeStatus.WAITING)) 
		{
			// check if node can start
			if (this.executive.canStart(node)) 
			{
				// check the actual bounds of the token
				this.executive.checkSchedule(node);
				// schedule node if possible 
				if (tau >= node.getStart()[0])
				{
					try
					{
						// schedule token start time
						this.executive.scheduleTokenStart(node, tau);
						// start node execution
						info("{Dispatcher} {tick: " + tick + "} {tau: " + tau + "} -> Start executing node at time: " + tau + "\n"
								+ "\t- node: " + node.getGroundSignature() + " (" + node + ")\n");
					}
					catch (TemporalConstraintPropagationException ex) {
						// set token as in execution and wait for feedbacks
						this.executive.updateNode(node, ExecutionNodeStatus.IN_EXECUTION);
						// create execution cause
						ExecutionFailureCause cause = new NodeStartOverflow(tick, node, tau);
						// throw execution exception
						throw new NodeObservationException(
								"The dispatched start time of the token does not comply with the plan:\n"
								+ "\t- start: " + tau + "\n"
								+ "\t- node: " + node + "\n", 
								cause);
					}
				}
				else  
				{
					// wait - not ready for dispatching
					debug("{Dispatcher} {tick: " + tick + " } {tau: " + tau + "} -> Start conditions satisifed but node schedule not ready for dispatching\n"
							+ "\t- node: " + node.getGroundSignature() + " (" + node + ")\n");
				}
			}
			else {
				
				// dispatching conditions not satisfied
				debug("{Dispatcher} {tick: " + tick + "} {tau: " + tau + "} -> Start conditions not satisfied\n"
						+ "\t- node: " + node.getGroundSignature() + " (" + node + ")\n");
			}
		}
	}
}
