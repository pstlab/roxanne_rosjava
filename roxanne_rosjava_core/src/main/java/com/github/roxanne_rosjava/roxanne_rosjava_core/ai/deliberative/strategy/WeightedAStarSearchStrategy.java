package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.solver.SearchSpaceNode;

/**
 * 
 * @author anacleto
 *
 */
public class WeightedAStarSearchStrategy extends SearchStrategy 
{
	/**
	 * 
	 */
	protected WeightedAStarSearchStrategy() {
		super("WeightedAStarSearchStrategy");
	}
	
	/**
	 * 
	 */
	@Override
	public void enqueue(SearchSpaceNode node) 
	{
		// compute a pessimistic estimation of flaw resolution cost
		double hValue = this.computePlanningHeuristics(node);
		// set heuristic estimation
		node.setHeuristic(hValue);
		// add the node to the priority queue
		this.fringe.offer(node);
	}
	
	/**
	 * 
	 */
	@Override
	public int compare(SearchSpaceNode o1, SearchSpaceNode o2) 
	{
		// set cost weight
		double weight = .3;
		// compute evaluation functions
		double f1 = (weight * o1.getCost()) + ((1.0 - weight) * o1.getHeuristic());
		double f2 = (weight * o2.getCost()) + ((1.0 - weight) * o2.getHeuristic());
		// compare evaluation functions 
		return f1 < f2 ? -1 : f1 > f2 ? 1 : 
			o1.getDepth() > o2.getDepth() ? -1 : o1.getDepth() < o2.getDepth() ? 1 : 0;
			 
	}
}