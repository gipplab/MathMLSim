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
        mathNode.setAttributes(node.getAttributes());
        mathNode.setValue(node.getFirstChild() != null ? node.getFirstChild().getTextContent().trim() : node.getTextContent().trim());
        mathNode.setDepth(depth);
        // iterate over all child elements
        XMLUtils.getChildElements(node).forEach(c -> mathNode.addChild(createMathNode(c, depth + 1)));
        return mathNode;
    }

}
