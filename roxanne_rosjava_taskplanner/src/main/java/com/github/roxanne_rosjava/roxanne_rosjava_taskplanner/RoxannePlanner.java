package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner;

import it.cnr.istc.pst.platinum.ai.deliberative.Planner;
import it.cnr.istc.pst.platinum.ai.deliberative.strategy.*;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.FrameworkLoggerConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.FlawSelectionHeuristicsConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.PlannerSolverConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.SearchStrategyConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.utils.log.FrameworkLoggingLevel;
import it.cnr.istc.pst.platinum.ai.deliberative.heuristic.HierarchicalFlawSelectionHeuristic;
import it.cnr.istc.pst.platinum.ai.deliberative.solver.PseudoControllabilityAwareSolver;
import it.cnr.istc.pst.platinum.ai.deliberative.strategy.DepthFirstSearchStrategy;


/**
 *
 */
@PlannerSolverConfiguration(
		solver = PseudoControllabilityAwareSolver.class
)
@FlawSelectionHeuristicsConfiguration(
		heuristics = HierarchicalFlawSelectionHeuristic.class
)
@SearchStrategyConfiguration(
		//strategy = StandardDeviationMinimizationSearchStrategy.class // DepthFirstSearchStrategy.class
		strategy = MakespanOptimization.class
)
@FrameworkLoggerConfiguration(		
		// set logging level
		level = FrameworkLoggingLevel.INFO
)
public class RoxannePlanner extends Planner
{
	/**
	 * 
	 */
	protected RoxannePlanner() {
		super();
	}
}
