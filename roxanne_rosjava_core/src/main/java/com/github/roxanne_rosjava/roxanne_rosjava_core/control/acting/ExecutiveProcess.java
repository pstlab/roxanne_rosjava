package com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting;

import it.cnr.istc.pst.platinum.ai.executive.Executive;
import it.cnr.istc.pst.platinum.ai.executive.ExecutiveBuilder;
import it.cnr.istc.pst.platinum.ai.executive.lang.ex.ExecutionException;
import it.cnr.istc.pst.platinum.ai.executive.lang.failure.ExecutionFailureCause;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNode;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNodeStatus;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.plan.SolutionPlan;
import it.cnr.istc.pst.platinum.ai.framework.protocol.lang.PlanProtocolDescriptor;
import it.cnr.istc.pst.platinum.control.lang.Goal;
import it.cnr.istc.pst.platinum.control.lang.GoalStatus;
import it.cnr.istc.pst.platinum.control.lang.TokenDescription;
import it.cnr.istc.pst.platinum.control.lang.ex.PlatformException;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author anacleto
 *
 */
public class ExecutiveProcess implements Runnable 
{
	private GoalOrientedActingAgent agent;
	private Class<? extends Executive> eClass;
	
	/**
	 * 
	 * @param eClass
	 * @param agent
	 */
	protected ExecutiveProcess(Class<? extends Executive> eClass, GoalOrientedActingAgent agent) {
		this.agent = agent;
		this.eClass = eClass;
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		boolean running = true;
		while(running)
		{
			try
			{
				// take a goal to plan for
				Goal goal = this.agent.waitGoal(GoalStatus.COMMITTED);
				// execute extracted goal
				boolean success = this.agent.execute(goal);
				// check executive result
				if (success) {
					// goal execution successfully complete
					this.agent.finish(goal);
				}
				else {
					// goal execution suspended due to some errors
					this.agent.suspend(goal);
				}
			}
			catch (InterruptedException ex) {
				running = false;
			}
			catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	/**
	 *
	 * @param goal
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws Exception
	 */
	protected void doExecute(Goal goal) 
			throws InterruptedException, ExecutionException, Exception
	{
		// get solution plan 
		SolutionPlan plan = goal.getPlan();
		// build executive
		Executive exec = ExecutiveBuilder.createAndSet(this.eClass, 0, plan.getHorizon());
		// export plan 
		PlanProtocolDescriptor desc = plan.export();
		System.out.println("Ready to start (timeline-based) plan execution... ");
		// set the executive according to the plan being executed
		exec.initialize(desc);
		
		// bind simulator if any
		if (this.agent.proxy != null) {
			// bind simulator
			exec.link(this.agent.proxy);
		}
		
		// run the executive starting at a given tick
		boolean complete = exec.execute(goal.getExecutionTick(), goal);
		// stop simulator if any
		if (this.agent.proxy != null) {
			// unlink from simulator
			exec.unlink();
		}
		
		// check execution result 
		if (!complete) 
		{
			// get failure cause
			ExecutionFailureCause cause = exec.getFailureCause();
			// set failure cause
			goal.setFailureCause(cause);
			// set repaired 
			goal.setRepaired(false);
			// set goal interruption tick
			goal.setExecutionTick(cause.getInterruptionTick());
			// set execution trace by taking into account executed nodes
			for (ExecutionNode node : exec.getNodes(ExecutionNodeStatus.EXECUTED)) {
				// add the node to the goal execution trace
				goal.addNodeToExecutionTrace(node);
			}
			
			// get the name of of goal components
			Set<String> components = new HashSet<>();
			for (TokenDescription t : goal.getTaskDescription().getGoals()) {
				components.add(t.getComponent());
			}
			
			// set execution trace by taking into account also (virtual) nodes in-execution
			for (ExecutionNode node : exec.getNodes(ExecutionNodeStatus.IN_EXECUTION)) {
				// do not consider nodes belonging to "goal component"
				if (!components.contains(node.getComponent())) {
					// add the node to the goal execution trace
					goal.addNodeToExecutionTrace(node);
				}
			}
			
			// throw exception
			throw new ExecutionException("Execution failure... try to repair the plan through replanning... \n"
					+ "\t- cause: " + cause + "\n", cause);
		}
	}
}
