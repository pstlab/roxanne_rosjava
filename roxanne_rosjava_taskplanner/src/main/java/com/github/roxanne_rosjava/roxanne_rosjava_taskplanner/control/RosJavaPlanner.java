package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.control;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline.PipelineFlawSelectionHeuristic;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.PseudoControllabilityAwareSolver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.SearchSpaceNode;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.Solver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy.DepthFirstSearchStrategy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.PlanDataBase;
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
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.Planner;

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
		level = FrameworkLoggingLevel.OFF
)
public class RosJavaPlanner extends Planner
{
	/**
	 * 
	 */
	protected RosJavaPlanner() {
		super();
	}
}
