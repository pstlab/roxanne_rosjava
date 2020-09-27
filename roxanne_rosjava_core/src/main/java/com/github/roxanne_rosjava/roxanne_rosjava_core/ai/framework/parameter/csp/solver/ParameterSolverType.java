package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.csp.solver;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.csp.solver.choco.v4.ChocoSolver;

/**
 * 
 * @author anacleto
 *
 */
public enum ParameterSolverType {

	/**
	 * CHOCHO wrapper for CSP constraints manager 
	 */
	CHOCHO_SOLVER(ChocoSolver.class.getName());
	
	private String cname;
	
	/**
	 * 
	 * @param cname
	 */
	private ParameterSolverType(String cname) {
		this.cname = cname;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getClassName() {
		return cname;
	}
}
