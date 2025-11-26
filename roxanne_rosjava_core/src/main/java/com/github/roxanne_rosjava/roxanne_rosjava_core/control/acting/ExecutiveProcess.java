package com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting;

import it.cnr.istc.pst.platinum.ai.executive.Executive;
import it.cnr.istc.pst.platinum.ai.executive.ExecutiveBuilder;
import it.cnr.istc.pst.platinum.ai.executive.lang.ex.ExecutionException;
import it.cnr.istc.pst.platinum.ai.executive.lang.failure.ExecutionFailureCause;
import it.cnr.istc.pst.platinum.ai.executive.lang.failure.ExecutionFailureCauseType;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNode;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNodeStatus;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.plan.SolutionPlan;
import it.cnr.istc.pst.platinum.ai.framework.protocol.lang.PlanProtocolDescriptor;
import it.cnr.istc.pst.platinum.control.lang.Goal;
import it.cnr.istc.pst.platinum.control.lang.GoalStatus;
import it.cnr.istc.pst.platinum.control.lang.TokenDescription;
import it.cnr.istc.pst.platinum.control.lang.ex.PlatformException;
import org.apache.commons.logging.Log;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ExecutiveProcess implements Runnable {

	private GoalOrientedActingAgent agent;
	private Class<? extends Executive> eClass;
	private Log log;

	/**
	 *
	 * @param eClass
	 * @param agent
	 * @param log
	 */
	protected ExecutiveProcess(Class<? extends Executive> eClass, GoalOrientedActingAgent agent, Log log) {
		this.agent = agent;
		this.eClass = eClass;
		this.log = log;
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		boolean running = true;
		while(running) {

			try {

				this.log.info("Start waiting for a plan to execute...");
				// take a goal to plan for
				Goal goal = this.agent.waitGoal(GoalStatus.COMMITTED);
				this.log.info("Start execution for goal:\n" +
						"- task description: " + goal.getTaskDescription() + "\n");
				// execute extracted goal
				boolean success = this.agent.execute(goal);
				// check executive result
				if (success) {

					// goal execution successfully complete
					this.log.info("Plan successfully executed... ");
					this.agent.finish(goal);

				} else {

					// goal execution suspended due to some errors
					this.log.info("Plan execution error, SUSPEND agent");
					this.agent.suspend(goal);
				}
			} catch (InterruptedException ex) {

				running = false;
			} catch (Exception ex) {
				this.log.error("Executive process error:\n" +
						"- message: " + ex.getMessage() + "\n");
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
			throws InterruptedException, ExecutionException {

		// get solution plan 
		SolutionPlan plan = goal.getPlan();
		// build executive
		Executive exec = ExecutiveBuilder.createAndSet(this.eClass, 0, plan.getHorizon());
		// export plan 
		PlanProtocolDescriptor desc = plan.export();
		// set the executive according to the plan being executed
		exec.initialize(desc);
		
		// bind simulator if any
		if (this.agent.proxy != null) {
			// bind simulator
			exec.link(this.agent.proxy);
		}

		// start plan execution
		this.log.info("Ready to start plan execution...\n" +
				"\t- goal execution tick: " + goal.getExecutionTick() + "\n");
		// set complete flag
		boolean complete = false;
		try {

			// run the executive starting at a given tick
			complete = exec.execute(goal.getExecutionTick(), goal);

		} catch (Exception ex) {
			complete = false;
			this.log.warn("Exception while executing the plan: \n- message: " + ex.getMessage() + "\n");
		}

		// stop simulator if necessary
		if (this.agent.proxy != null) {
			// unlink from simulator
			exec.unlink();
		}
		
		// check execution result 
		if (!complete) {

			// get failure cause
			ExecutionFailureCause cause = exec.getFailureCause();
			// set failure cause
			goal.setFailureCause(cause);
			// check failure cause
			if (cause.equals(ExecutionFailureCauseType.NODE_EXECUTION_ERROR)) {

				// clear executive clock
				goal.clearExecutionTrace();
				goal.setExecutionTick(0);
			}
			else {

				// prepare for planning adaptation/re-planning
				goal.setRepaired(false);
				// set goal interruption tick
				goal.setExecutionTick(cause.getInterruptionTick());

				// set execution trace by taking into account executed nodes
				for (ExecutionNode node : exec.getNodes(ExecutionNodeStatus.EXECUTED)) {
					// add the node to the goal execution trace
					goal.addNodeToExecutionTrace(node);
				}

				// get the name of goal components
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
			}

			// throw exception
			throw new ExecutionException("Execution failure... \n"
					+ "\t- cause: " + cause + "\n", cause);
		}
	}
}
