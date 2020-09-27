package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.knowledge;

/**
 * 
 * @author anacleto
 *
 */
public enum DomainKnowledgeType {

	/**
	 * 
	 */
	STATIC(StaticDomainKnowledge.class.getName());
	
	private String clazz;
	
	private DomainKnowledgeType(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * 
	 * @return
	 */
	public String getClassName() {
		return clazz;
	}
}
