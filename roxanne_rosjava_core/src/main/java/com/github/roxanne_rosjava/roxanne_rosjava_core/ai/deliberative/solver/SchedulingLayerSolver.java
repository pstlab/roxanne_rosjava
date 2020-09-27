package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline.PipelineFlawSelectionHeuristic;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy.GreedyDepthSearchStrategy;
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
		strategy = GreedyDepthSearchStrategy.class
)
public class SchedulingLayerSolver extends ModularSolver 
{
	/**
	 * 
	 * @param timeout
	 */
	protected SchedulingLayerSolver(long timeout) {
		super("SchedulingLayerSolver", timeout); 
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
				FlawType.TIMELINE_OVERFLOW,
				FlawType.RESOURCE_PRODUCTION_UPDATE,
				FlawType.RESOURCE_PRODUCTION_PLANNING,
				FlawType.RESOURCE_OVERFLOW,
		};
	}
	
}	
