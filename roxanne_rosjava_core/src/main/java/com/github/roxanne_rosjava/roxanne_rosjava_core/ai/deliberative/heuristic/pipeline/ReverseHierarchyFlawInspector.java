package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.heuristic.pipeline;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.lang.flaw.Flaw;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ex.UnsolvableFlawException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.DomainComponent;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.knowledge.DomainKnowledge;

/**
 * 
 * @author anacleto
 *
 */
public class ReverseHierarchyFlawInspector extends FlawInspector 
{
	/**
	 * 
	 */
	protected ReverseHierarchyFlawInspector() {
		super("ReverseHierarchyFlawInspector");
	}
	
	/**
	 * 
	 */
	@Override
	public Set<Flaw> detectFlaws()
			throws UnsolvableFlawException
	{
		// filtered set
		Set<Flaw> set = new HashSet<>();
		// get knowledge
		DomainKnowledge knowledge = this.pdb.getDomainKnowledge();
		// get the hierarchy
		List<DomainComponent>[] hierarchy = knowledge.getDomainHierarchy();
		// detect flaws according to the computed hierarchy of the domain
		for (int index = hierarchy.length - 1; index >= 0 && set.isEmpty(); index--) 
		{
			// extract flaws of equivalent components
			for (DomainComponent component : hierarchy[index]) {
				// detect flaws
				set.addAll(component.detectFlaws());
			}
		}
		
		// get flaws
		return set;
	}
	
	/**
	 * 
	 */
	@Override
	public Set<Flaw> check() {
		// filtered set
		Set<Flaw> set = new HashSet<>();
		// get knowledge
		DomainKnowledge knowledge = this.pdb.getDomainKnowledge();
		// get the hierarchy
		List<DomainComponent>[] hierarchy = knowledge.getDomainHierarchy();
		// detect flaws according to the computed hierarchy of the domain
		for (int index = hierarchy.length - 1; index >= 0 && set.isEmpty(); index--) 
		{
			// extract flaws of equivalent components
			for (DomainComponent component : hierarchy[index]) {
				// detect flaws
				set.addAll(component.checkFlaws());
			}
		}
		
		// get flaws
		return set;
	}
	
	/**
	 * 
	 */
	@Override
	public Set<Flaw> filter(Collection<Flaw> flaws) 
	{
		// filtered set
		Set<Flaw> set = new HashSet<>();
		// get knowledge
		DomainKnowledge knowledge = this.pdb.getDomainKnowledge();
		// get the hierarchy
		List<DomainComponent>[] hierarchy = knowledge.getDomainHierarchy();
		// filter flaws according to the hierarchy of the related component
		for (int hlevel = hierarchy.length - 1; hlevel >= 0; hlevel--)
		{
			// extract flaws of equivalent components
			for (DomainComponent c : hierarchy[hlevel]) 
			{
				// check component's flaws
				for (Flaw flaw : flaws) 
				{
					// check flaw's component
					if (flaw.getComponent().equals(c)) {
						// add flaw
						set.add(flaw);
					}
				}
			}
		}
		
		// get filtered set
		return set;
	}
}