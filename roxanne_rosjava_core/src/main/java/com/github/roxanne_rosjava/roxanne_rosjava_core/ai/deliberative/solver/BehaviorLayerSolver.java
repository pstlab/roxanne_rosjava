package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline.PipelineFlawSelectionHeuristic;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy.CostDepthSearchStrategy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.FlawSelectionHeuristicsConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative.SearchStrategyConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.flaw.FlawType;

/**
 * 
 * @author alessandroumbrico
 *
 */
@FlawSelectionHeuristicsConfiguration(
	heuristics = PipelineFlawSelectionHeuristic.class
)
@SearchStrategyConfiguration(
		strategy = CostDepthSearchStrategy.class
)
public class BehaviorLayerSolver extends ModularSolver 
{
	/**
	 * 
	 * @param timeout
	 */
	protected BehaviorLayerSolver(long timeout) {
		super("BehaviorLayerSolver", timeout);
	}
	
	/**
	 * 
	 */
	@Override
	protected void init() {
		// set last node
		this.last = null;
		// set flaw types
		this.fTypes = new FlawType[] {
			FlawType.PLAN_REFINEMENT,	
			FlawType.TIMELINE_BEHAVIOR_PLANNING
		};
	}
	
}	
