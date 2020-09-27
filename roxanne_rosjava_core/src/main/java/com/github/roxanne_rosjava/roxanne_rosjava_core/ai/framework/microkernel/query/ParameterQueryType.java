package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.lang.query.CheckValuesParameterQuery;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.lang.query.ComputeSolutionParameterQuery;

/**
 * 
 * @author anacleto
 *
 */
public enum ParameterQueryType {

	/**
	 * Check the current values of a parameter
	 */
	CHECK_PARAMETER_VALUES(CheckValuesParameterQuery.class.getName()),
	
	/**
	 * Compute possible assignments of all parameters
	 */
	COMPUTE_SOLUTION(ComputeSolutionParameterQuery.class.getName());
	
	private String cname;
	
	private ParameterQueryType(String cname) {
		this.cname = cname;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getQueryClassName() {
		return cname;
	}
}
