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

        // compute plan cost on robot timeline
        double c1 = n1.getCost().get(this.pdb.getComponentByName("base")) +
                n1.getHeuristicCost().get(this.pdb.getComponentByName("base"))[0];

        double c2 = n2.getCost().get(this.pdb.getComponentByName("base")) +
                n2.getHeuristicCost().get(this.pdb.getComponentByName("base"))[0];

        // compute makespan on robot base timeline
        double m1 = n1.getMakespan().get(this.pdb.getComponentByName("base"))[0]
                + n1.getHeuristicMakespan().get(this.pdb.getComponentByName("base"))[0];

        double m2 = n1.getMakespan().get(this.pdb.getComponentByName("base"))[0] +
                n2.getHeuristicMakespan().get(this.pdb.getComponentByName("base"))[0];


        // search in depth by minimizing the makespan
        return n1.getDepth() > n2.getDepth() ? -1 : n1.getDepth() < n2.getDepth() ? 1 :
                m1 < m2 ? -1 : m1 > m2 ? 1 :
                    c1 < c2 ? -1 : c1 > c2 ? 1 : 0;
    }
}
