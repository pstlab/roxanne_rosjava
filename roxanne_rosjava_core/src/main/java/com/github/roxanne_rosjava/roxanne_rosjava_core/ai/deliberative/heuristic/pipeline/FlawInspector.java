package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline;

import java.util.Collection;
import java.util.Set;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.framework.PlanDataBasePlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.flaw.Flaw;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ex.UnsolvableFlawException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.PlanDataBase;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.FrameworkObject;

/**
 * 
 * @author anacleto
 *
 */
public abstract class FlawInspector extends FrameworkObject 
{
	@PlanDataBasePlaceholder
	protected PlanDataBase pdb;
	
	private String label;
	
	/**
	 * 
	 * @param type
	 */
	protected FlawInspector(String label) {
		this.label = label;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * The filter applies the encapsulated criteria to an already 
	 * computed set of flaws
	 * 
	 * @param flaws
	 * @return
	 */
	public abstract Set<Flaw> filter(Collection<Flaw> flaws);
	
	/**
	 * The filter applies the encapsulated criteria to the flaws
	 * detected by directly querying the plan data-base
	 * 
	 * @return
	 * @throws UnsolvableFlawException
	 */
	public abstract Set<Flaw> detectFlaws() 
			throws UnsolvableFlawException;
	
	/**
	 * Check flaws without computing possible solutions
	 * 
	 * @return
	 */
	public abstract Set<Flaw> check();
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "{ \"label\": \"" +  this.label + "\" }";
	}
}
