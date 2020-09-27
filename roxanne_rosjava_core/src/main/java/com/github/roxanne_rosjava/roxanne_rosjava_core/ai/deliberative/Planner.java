package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline.PipelineFlawSelectionHeuristic;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.PseudoControllabilityAwareSolver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.SearchSpaceNode;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.Solver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy.DepthFirstSearchStrategy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.PlanDataBase;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.FrameworkObject;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.FrameworkLoggerConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.FlawSelectionHeuristicsConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.PlannerSolverConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.SearchStrategyConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.deliberative.PlannerSolverPlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.framework.PlanDataBasePlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.ex.NoSolutionFoundException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.plan.PlanControllabilityType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.plan.SolutionPlan;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.utils.log.FrameworkLoggingLevel;


/**
 * 
 * @author anacleto
 *
 */
@PlannerSolverConfiguration(
		solver = PseudoControllabilityAwareSolver.class,
		timeout = 180000
)
@FlawSelectionHeuristicsConfiguration(
		heuristics = PipelineFlawSelectionHeuristic.class
)
@SearchStrategyConfiguration(
		strategy = DepthFirstSearchStrategy.class
)
@FrameworkLoggerConfiguration(
		// set logging level
		level = FrameworkLoggingLevel.INFO
)
public class Planner extends FrameworkObject
{
	@PlanDataBasePlaceholder
	protected PlanDataBase pdb;
	
	@PlannerSolverPlaceholder
	protected Solver solver;
	
	protected SearchSpaceNode currentSolution;			// current solution found
	
	/**
	 * 
	 */
	protected Planner() {
		super();
		this.currentSolution = null;
	}
	 
	/**
	 * Display the current plan
	 */
	public void display() {
		// display the current plan
		this.pdb.display();
	}
	
	/**
	 * The method starts the planning process and return the solution plan if any.
	 * 
	 * If no solution plan is found the method throws an exception
	 * 
	 * @return
	 * @throws NoSolutionFoundException
	 */
	public SolutionPlan plan()
			throws NoSolutionFoundException 
	{
		// get time 
		long start = System.currentTimeMillis();
		// find a solution to the planning problem
		this.currentSolution = this.solver.solve();
		
		// extract solution plan
		SolutionPlan plan = this.pdb.getSolutionPlan();
		plan.setControllability(PlanControllabilityType.PSEUDO_CONTROLLABILITY);
		
		// check solving time 
		long time = System.currentTimeMillis() - start;
		plan.setSolvingTime(time);
		// get the solution plan
		return plan;
	}
	
	/**
	 * The method returns a structure representing the current plan. 
	 * 
	 * @return
	 */
	public SolutionPlan getCurrentPlan() {
		// get current plan
		return this.pdb.getSolutionPlan();
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		// get a description of the plan data base
		return this.pdb.toString();
	}
}
