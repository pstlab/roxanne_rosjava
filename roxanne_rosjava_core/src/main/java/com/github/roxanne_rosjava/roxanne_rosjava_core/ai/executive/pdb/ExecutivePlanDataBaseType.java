package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb;

/**
 * 
 * @author anacleto
 *
 */
public enum ExecutivePlanDataBaseType 
{
	/**
	 * 
	 */
	PLATINUM(ExecutivePlanDataBase.class.getName());
	
	private String className;
	
	/**
	 * 
	 * @param className
	 */
	private ExecutivePlanDataBaseType(String className) {
		this.className = className;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	
}