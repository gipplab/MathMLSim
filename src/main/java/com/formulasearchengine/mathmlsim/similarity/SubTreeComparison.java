package com.formulasearchengine.mathmlsim.similarity;

import com.formulasearchengine.mathmlsim.similarity.node.MathNode;
import com.formulasearchengine.mathmlsim.similarity.node.MathNodeGenerator;
import com.formulasearchengine.mathmlsim.similarity.result.Match;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simplistic approach to find similar subtrees between two math expression trees.
 * <br/>
 * The algorithm will use {@link MathNode}s.
 *
 * @author Vincent Stange
 */
public class SubTreeComparison {

    private final Match.Type type;

    public SubTreeComparison(String type) {
        this.type = Match.Type.valueOf(type);
    }

    /**
     * Get a list of similarities between a reference and comparison document.
     * This method will take an extra step to convert the CMMLInfo document to a MathNode.
     *
     * @param refDoc        Reference CMMLInfo document
     * @param compDoc       Comparison CMMLInfo document
     * @param onlyOperators find similarities only between operations, leafs are not checked
     * @return list of similarities, list can be empty but never null
     */
    public List<Match> getSimilarities(CMMLInfo refDoc, CMMLInfo compDoc, boolean onlyOperators) {
        // switch from the CMMLInfo document to our MathNode representation
        MathNode refTree = MathNodeGenerator.generateMathNode(refDoc);
        MathNode compTree = MathNodeGenerator.generateMathNode(compDoc);
        return getSimilarities(refTree, compTree, onlyOperators);
    }

    /**
     * Get a list of similarities between the reference and comparison tree.
     *
     * @param refTree       Reference MathNode tree
     * @param compTree      Comparison MathNode tree
     * @param onlyOperators find similarities only between operations, leafs are not checked
     * @return list of similarities, list can be empty but never null
     */
    public List<Match> getSimilarities(MathNode refTree, MathNode compTree, boolean onlyOperators) {
        List<Match> similarities = new ArrayList<>();
        findSimilarities(refTree, compTree, similarities, false, onlyOperators);
        return similarities;
    }

    /**
     * Recursive method that goes along every node of the reference tree and tries to find
     * identical subtree with the comparison tree.
     *
     * @param refTree       Reference MathNode tree
     * @param comTree       Comparison MathNode tree
     * @param similarities  List of similarities, will be filled during process.
     * @param holdRefTree   Hold the reference tree in position and only iterate over the comparison tree
     * @param onlyOperators Find similarities only between operations, no single identifier (end leafs) are checked
     * @return true - if the current aTree ad bTree are identical subtrees, false otherwise
     */
    boolean findSimilarities(MathNode refTree, MathNode comTree, List<Match> similarities, boolean holdRefTree, boolean onlyOperators) {
        if (isIdenticalTree(refTree, comTree)) {
            // hit!
            comTree.setMarked(true);
            similarities.add(new Match(refTree, comTree, type));
            return true;
        }
        // iterate the comparison tree over the current node from the ref tree
        for (MathNode bTreeChild : comTree.getChildren()) {
            // don't look at leafs if it is already marked
            // or we only want to compare branching nodes
            if (bTreeChild.isMarked() || onlyOperators && bTreeChild.isLeaf())
                continue;
            // go deeper in the comp. tree but hold the ref tree
            if (findSimilarities(refTree, bTreeChild, similarities, true, onlyOperators)) {
                return true;
            }
        }

        if (!holdRefTree) {
            // go deeper in the reference tree
            for (MathNode aTreeChild : refTree.getChildren()) {
                if (onlyOperators && aTreeChild.isLeaf())
                    continue;

                findSimilarities(aTreeChild, comTree, similarities, false, onlyOperators);
            }
        }
        return false;
    }

    /**
     * Are aTree and bTree identical subtrees? If the root node is equal,
     * all subsequent children will be compared.
     *
     * @param aTree first MathNode tree
     * @param bTree second MathNode tree
     * @return true - if both trees are identical subtrees, false otherwise
     */
    boolean isIdenticalTree(MathNode aTree, MathNode bTree) {
        // first check if they have the same number of children
        if (aTree.equals(bTree) && aTree.getChildren().size() == bTree.getChildren().size()) {
            if (aTree.isOrderSensitive()) {
                // all children order sensitive
                for (int i = 0; i < aTree.getChildren().size(); i++) {
                    if (!isIdenticalTree(aTree.getChildren().get(i), bTree.getChildren().get(i))) {
                        return false;
                    }
                }
            } else {
                // order insensitive
                List<MathNode> bChildren = new ArrayList<>(bTree.getChildren());
                OUTER:
                for (MathNode aChild : aTree.getChildren()) {
                    for (MathNode bChild : filterSameChildren(aChild, bChildren)) {
                        if (isIdenticalTree(aChild, bChild)) {
                            // found an identical child
                            bChildren.remove(bChild);
                            continue OUTER;
                        }
                    }
                    // aChild is missing in bChildren
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Filter for similar nodes from a list. Only the node itself will be compared.
     * We will not look at their respective children.
     * <br/>
     * This method is used when we want to compare children from different nodes
     * out of order.
     *
     * @param searchNode node we want to find.
     * @param list       list we want to search.
     * @return new list with the same node we search for.
     */
    List<MathNode> filterSameChildren(MathNode searchNode, List<MathNode> list) {
        return list.stream().filter(searchNode::equals).collect(Collectors.toList());
    }

}
