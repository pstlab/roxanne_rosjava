package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.ai.deliberative;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.Planner;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline.PipelineFlawSelectionHeuristic;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.PseudoControllabilityAwareSolver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy.GreedyDepthSearchStrategy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.FrameworkLoggerConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.FlawSelectionHeuristicsConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.PlannerSolverConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.SearchStrategyConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.utils.log.FrameworkLoggingLevel;




@PlannerSolverConfiguration(
		solver = PseudoControllabilityAwareSolver.class
//		timeout = 180000
)
@FlawSelectionHeuristicsConfiguration(
		heuristics = PipelineFlawSelectionHeuristic.class
//		heuristics = HierarchicalFlawSelectionHeuristic.class
)
@SearchStrategyConfiguration(
//		strategy = DepthFirstSearchStrategy.class
		strategy = GreedyDepthSearchStrategy.class
//		strategy = MakespanGreedyDepthSearchStrategy.class
//		strategy = WeightedAStarSearchStrategy.class
//		strategy = CostDepthSearchStrategy.class
//		strategy = WorkloadBalancingSearchStrategy.class
//		strategy = RiskAwarenessSearchStrategy.class
)
@FrameworkLoggerConfiguration(		
		// set logging level
		level = FrameworkLoggingLevel.OFF
)
public class PlannerTest extends Planner {

	/**
	 * 
	 */
	protected PlannerTest() {
		super();
	}
}
