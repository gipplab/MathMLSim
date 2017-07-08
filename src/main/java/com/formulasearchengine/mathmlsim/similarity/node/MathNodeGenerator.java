package com.formulasearchengine.mathmlsim.similarity.node;

import com.formulasearchengine.mathmlsim.similarity.util.CMMLHelper;
import com.formulasearchengine.mathmlsim.similarity.util.XMLUtils;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.Optional;

/**
 * This class converts MathML (Content MathML) into our own
 * math expression tree representation of a mathematical
 * formula. (CMML document > MathNode)
 * <br/>
 * This is purely a utility class and therefore has no public constructor.
 *
 * @author Vincent Stange
 */
public class MathNodeGenerator {

    private static Logger logger = Logger.getLogger(MathNodeGenerator.class);

    private MathNodeGenerator() {
        // not visible, utility class only
    }

    /**
     * Create a math expression tree (MET) starting from an CMMLInfo document.
     *
     * @param cmmlInfo CMMLInfo document
     * @return first MathNode representing the root of the MET, or null
     */
    public static MathNode generateMathNode(CMMLInfo cmmlInfo) {
        Optional.ofNullable(cmmlInfo).orElseThrow(() -> new NullPointerException("cmml document is null"));
        try {
            return generateMathNode(CMMLHelper.getFirstApplyNode(cmmlInfo));
        } catch (XPathExpressionException e) {
            logger.error("could not generate a math node tree", e);
            return null;
        }
    }

    /**
     * Create a math expression tree (MET) starting from the root element of a
     * Content MathML document.
     *
     * @param applyRoot root apply element of a CMML document.
     * @return first MathNode representing the root of the MET
     */
    public static MathNode generateMathNode(Node applyRoot) {
        // null check
        Optional.ofNullable(applyRoot).orElseThrow(() -> new NullPointerException("apply element is missing"));

        MathNode mathNode = createMathNode(applyRoot, 0);
        // compute the maximum depth at least once after creation to define it for each branch.
        if (mathNode != null) {
            mathNode.getMaxDepth();
        }
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
     * Creates an abstract MathNode tree. This methode stands as an
     * alternative to CMMLInfo.abstract2CD() and tries to improve the
     * versatily during a comparison.
     * <br/>
     * In an abstract MathNode the tag-name will be overwritten with
     * the specific CD tag-name but the original value remains.
     * Furthermore the equal()-method will change - the value will
     * not be considered!
     *
     * @param mathNode MathNode tree to be converted.
     * @return self reference of the MathNode after the conversion.
     */
    public static MathNode toAbstract(MathNode mathNode) {
        // change the tag-name to the "cd" attribute value, if it exists
        String cd = mathNode.getAttribute("cd");
        if (cd != null && !cd.isEmpty()) {
            mathNode.setName(cd);
        }
        // set the node to strict - the behavior will change
        mathNode.setAbstractNode();
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
    public static String printMathNode(MathNode node, String indent) {
        StringBuilder sb = new StringBuilder(indent + node.toString() + "\n");
        node.getChildren().forEach(n -> sb.append(printMathNode(n, indent + " ")));
        return sb.toString();
    }
}
