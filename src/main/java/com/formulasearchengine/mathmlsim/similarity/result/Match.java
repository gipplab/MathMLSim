package com.formulasearchengine.mathmlsim.similarity.result;

import com.formulasearchengine.mathmlsim.similarity.node.MathNode;
import com.google.common.collect.HashMultiset;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON wrapper for matches between two math expression trees.
 * The Id relates to the reference MET, where submatches refer
 * to the comparison MET.
 *
 * @author Vincent Stange
 */
public class Match {

    public enum Type {
        similar, identical
    }

    public String id = "";

    public int depth = 0;

    public double coverage = 0;

    public List<SubMatch> matches = new ArrayList<>();

    /**
     * Create a match. A match must always contain sub-matches.
     * The match will refer to the part of the reference tree, whereas the sub-match will
     * refer to the part of the comparison tree.
     *
     * @param refTree partial reference tree (or full tree)
     * @param compTree partial comparison tree (or full tree)
     * @param type
     */
    public Match(MathNode refTree, MathNode compTree, Type type) {
        this.id = refTree.getId();
        this.depth = refTree.getDepth();
        this.coverage = getCoverage(refTree.getLeafs(), compTree.getLeafs());
        // initialize with the first match
        addMatch(new SubMatch(refTree, compTree, type));
    }

    public SubMatch addMatch(SubMatch subMatch) {
        this.matches.add(subMatch);
        return subMatch;
    }

    /**
     * This method is still in testing.
     * <br/>
     * Calculate the coverage factor between two trees, whereas only their leafs
     * are considered. Leafs are typically identifiers or constants.
     *
     * @param refLeafs all leafs from the partial (or full) reference tree
     * @param compLeafs all leafs from the partial (or full) comparison tree
     * @return coverage factor between 0 to 1, 1 is a full-match
     */
    public static double getCoverage(List<MathNode> refLeafs, List<MathNode> compLeafs) {
        if (compLeafs.size() == 0) {
            return 1.;
        }
        HashMultiset<MathNode> tmp = HashMultiset.create();
        tmp.addAll(compLeafs);
        tmp.removeAll(refLeafs);
        return 1 - Double.valueOf(tmp.size()) / Double.valueOf(compLeafs.size());
    }

}
