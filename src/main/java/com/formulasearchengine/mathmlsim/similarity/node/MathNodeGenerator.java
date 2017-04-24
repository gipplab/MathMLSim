package com.formulasearchengine.mathmlsim.similarity.node;

import com.formulasearchengine.mathmlsim.similarity.util.CMMLHelper;
import com.formulasearchengine.mathmlsim.similarity.util.XMLUtils;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;
import org.w3c.dom.Node;

/**
 * This class converts MathML (Content MathML) into our own  math expression tree representation
 * of a mathematical formula. (CMML document > MathNode)
 *
 * @author Vincent Stange
 */
public class MathNodeGenerator {

    /**
     * Create a math expression tree (MET) starting from an CMMLInfo document.
     *
     * @param cmmlInfo CMMLInfo document
     * @return first MathNode representing the root of the MET
     */
    public static MathNode generateMathNode(CMMLInfo cmmlInfo) {
        return generateMathNode(CMMLHelper.getFirstCmmlNode(cmmlInfo));
    }

    /**
     * Create a math expression tree (MET) starting from the root element of a
     * Content MathML document.
     *
     * @param cmmlRoot root element of a CMML document.
     * @return first MathNode representing the root of the MET
     */
    public static MathNode generateMathNode(Node cmmlRoot) {
        MathNode mathNode = createMathNode(cmmlRoot, 0);
        // compute the maximum depth at least once after creation to define it for each branch.
        mathNode.getMaxDepth();
        return mathNode;
    }

    /**
     * Recursive method to create a math expression tree (MET). Every child
     * and all attributes are considered in the conversion.
     *
     * @param node  current xml node in cmml, typically the root element
     * @param depth current depth of the math tree
     * @return converted MathNode we use in this application
     */
    private static MathNode createMathNode(Node node, int depth) {
        MathNode mathNode = new MathNode();
        mathNode.setName(node.getNodeName());
        if (mathNode.getName().equalsIgnoreCase("annotation-xml")) {
            // this can be an additional tag from the strict cmml conversion
            // - node should not used.
            return null;
        }
        mathNode.setAttributes(node.getAttributes());
        mathNode.setValue(node.getFirstChild() != null ? node.getFirstChild().getTextContent().trim() : node.getTextContent().trim());
        mathNode.setDepth(depth);
        // iterate over all child elements
        XMLUtils.getChildElements(node).forEach(c -> mathNode.addChild(createMathNode(c, depth + 1)));
        return mathNode;
    }

    /**
     * TODO
     *
     * @param mathNode
     * @return
     */
    public static MathNode toAbstract(MathNode mathNode) {
        // change the tag-name to the "cd" attribute value, if it exists
        String cd = mathNode.getAttribute("cd");
        if (cd != null && !cd.isEmpty()) {
            mathNode.setName(cd);
        }
        // set the node to strict - the behavior will change
        mathNode.setStrict();
        // for every child the same
        mathNode.getChildren().forEach(MathNodeGenerator::toAbstract);
        return mathNode;
    }

    /**
     * Converts a MathNode into a an simplistic indented tree
     * representation of itself.
     *
     * @param node   Node to begin with and onwards for all children of it.
     * @param indent starting line used as an indent (e.g. start with "")
     * @return return a tree representation of itself
     */
    public static String print(MathNode node, String indent) {
        StringBuilder sb = new StringBuilder(indent + node.toString() + "\n");
        node.getChildren().forEach(n -> sb.append(print(n, indent + " ")));
        return sb.toString();
    }

}
