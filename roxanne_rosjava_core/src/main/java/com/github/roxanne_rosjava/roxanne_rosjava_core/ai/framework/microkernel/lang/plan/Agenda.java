package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.plan;

import java.util.ArrayList;
import java.util.List;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.relations.Relation;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.Decision;

/**
 * 
 * @author anacleto
 *
 */
public class Agenda 
{
	private List<Decision> decisions;
	private List<Relation> relations;
	
	/**
	 * 
	 */
	public Agenda() {
		this.decisions = new ArrayList<>();
		this.relations = new ArrayList<>();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Decision> getDecisions() {
		return new ArrayList<>(this.decisions);
	}
	
	/**
	 * 
	 * @param goal
	 */
	public void add(Decision goal) {
		this.decisions.add(goal);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Relation> getRelations() {
		return relations;
	}
	
	/**
	 * 
	 * @param rel
	 */
	public void add(Relation rel) {
		this.relations.add(rel);
	}
	
}
