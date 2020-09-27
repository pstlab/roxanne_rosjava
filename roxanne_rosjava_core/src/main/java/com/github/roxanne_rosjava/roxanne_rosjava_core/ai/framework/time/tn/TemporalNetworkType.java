package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn;

/**
 * 
 * @author anacleto
 *
 */
public enum TemporalNetworkType 
{
	/**
	 * Simple Temporal Network with Uncertainty 
	 */
	STNU(SimpleTemporalNetworkWithUncertainty.class.getName());
	
	private String name;
	
	private TemporalNetworkType(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getClassName() {
		return this.name;
	}
}
