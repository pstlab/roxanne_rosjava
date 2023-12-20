package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner;

import it.cnr.istc.pst.platinum.ai.deliberative.solver.SearchSpaceNode;
import it.cnr.istc.pst.platinum.ai.deliberative.strategy.SearchStrategy;

/**
 *
 */
public class MakespanOptimization extends SearchStrategy {

    protected MakespanOptimization() {super("MakespanOptimizationSearchStrategy");}

    /**
     *
     * @param node
     */
    @Override
    public void enqueue(SearchSpaceNode node) {
        // compute heuristic cost
        node.setHeuristicCost(this.computeHeuristicCost(node));
        // compute heuristic node
        node.setHeuristicMakespan(this.computeHeuristicMakespan(node));
        this.fringe.offer(node);
    }

    /**
     *
     * @param n1
     * @param n2
     * @return
     */
    @Override
    public int compare(SearchSpaceNode n1, SearchSpaceNode n2) {
        // search in depth by minimizing the makespan
        return n1.getDepth() > n2.getDepth() ? -1 : n1.getDepth() < n2.getDepth() ? 1 :
                n1.getPlanMakespan()[0] < n2.getPlanMakespan()[0] ? -1 : n1.getPlanMakespan()[0] > n2.getPlanMakespan()[0] ? 1 : 0;
    }
}
