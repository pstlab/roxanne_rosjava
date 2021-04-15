package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner;

import it.cnr.istc.pst.platinum.ai.deliberative.Planner;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.FrameworkLoggerConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.FlawSelectionHeuristicsConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.PlannerSolverConfiguration;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.annotation.cfg.deliberative.SearchStrategyConfiguration;

/**
 * 
 * @author anacleto
 *
 */
@PlannerSolverConfiguration(
		timeout = 180000
)
@FlawSelectionHeuristicsConfiguration
@SearchStrategyConfiguration
@FrameworkLoggerConfiguration
public class RosJavaPlanner extends Planner
{
	/**
	 * 
	 */
	protected RosJavaPlanner() {
		super();
	}
}
