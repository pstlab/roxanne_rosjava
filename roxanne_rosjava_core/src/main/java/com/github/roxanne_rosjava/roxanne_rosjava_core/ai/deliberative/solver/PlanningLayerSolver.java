package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline.PipelineFlawSelectionHeuristic;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy.WeightedAStarSearchStrategy;
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
		strategy = WeightedAStarSearchStrategy.class
)
public class PlanningLayerSolver extends ModularSolver 
{
	/**
	 * 
	 * @param timeout
	 */
	protected PlanningLayerSolver(long timeout) {
		super("PlanningLayerSolver", timeout);
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
				FlawType.RESOURCE_PRODUCTION_PLANNING,
				FlawType.RESOURCE_PRODUCTION_UPDATE,
				FlawType.RESOURCE_OVERFLOW,
				FlawType.PLAN_REFINEMENT,
				FlawType.TIMELINE_OVERFLOW
		};
	}
	
}	