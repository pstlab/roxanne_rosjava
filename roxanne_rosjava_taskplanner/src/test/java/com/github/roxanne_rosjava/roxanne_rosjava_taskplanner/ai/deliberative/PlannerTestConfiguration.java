package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.ai.deliberative;


import it.cnr.istc.pst.platinum.ai.deliberative.Planner;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.FrameworkLoggerConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.FlawSelectionHeuristicsConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.PlannerSolverConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.SearchStrategyConfiguration;

@PlannerSolverConfiguration(
		timeout = 180000
)
@FlawSelectionHeuristicsConfiguration
@SearchStrategyConfiguration
@FrameworkLoggerConfiguration
public class PlannerTestConfiguration extends Planner {

	/**
	 * 
	 */
	protected PlannerTestConfiguration() {
		super();
	}
}
