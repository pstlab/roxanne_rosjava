package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.control.acting;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.Planner;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.PlannerBuilder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.PlanDataBase;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.NoSolutionFoundException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.plan.SolutionPlan;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.lang.*;

/**
 * 
 * @author anacleto
 *
 */
public class DeliberativeProcess implements Runnable 
{
	private GoalOrientedActingAgent agent;
	private Class<? extends Planner> pClass;
	private boolean displayPlan;
	
	/**
	 * 
	 * @param pClass
	 * @param displayPlan
	 * @param agent
	 */
	protected DeliberativeProcess(Class<? extends Planner> pClass, boolean displayPlan, GoalOrientedActingAgent agent) {
		this.agent = agent;
		this.pClass = pClass;
		this.displayPlan = displayPlan;
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
				Goal goal = this.agent.waitGoal(GoalStatus.SELECTED);
				System.out.println("... deliberating on goal\n" + goal + "\n"); 
				// deliberate extracted goal
				boolean success = this.agent.plan(goal);
				// check deliberative result
				if (success) {
					// commit planned goal
					this.agent.commit(goal);
				}
				else {
					// deliberative failure abort goal
					this.agent.abort(goal);
				}
			}
			catch (InterruptedException ex) {
				running = false;
			}
		}
	}
	
	/**
	 * 
	 * @param pdb
	 * @throws NoSolutionFoundException
	 */
	protected SolutionPlan doPlan(PlanDataBase pdb) 
			throws NoSolutionFoundException 
	{
		// setup planner on the current status of the plan database
		Planner planner = PlannerBuilder.createAndSet(this.pClass, pdb);
		// start planning 
		SolutionPlan plan = planner.plan();
		// display plan 
		if (this.displayPlan) {
			planner.display();
		}
		
		// get plan found
		return plan;
	}
}
