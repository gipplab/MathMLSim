package com.formulasearchengine.mathmlsim.similarity.node;

import com.formulasearchengine.mathmlsim.similarity.util.CMMLHelper;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;
import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Node;

import java.util.function.Function;

/**
 * @author Vincent Stange
 */
public class MathNodeGeneratorTest {

    @Test
    public void generateMathNode_normal() throws Exception {
        // mathml from latexml and just get the cmml semantics
        testConverterOnFile(CMMLHelper::getCmml, "mathnode1");
    }

    @Test
    public void generateMathNode_strict() throws Exception {
        // mathml from latexml and get the strict cmml semantics
        testConverterOnFile(CMMLHelper::getAbstractCmml, "mathnode2");
    }

    @Test
    public void generateMathNode_mathoid_normal() throws Exception {
        // mathml from mathoid and get the cmml semantics
        testConverterOnFile(CMMLHelper::getCmml, "mathnode3");
    }

    @Test
    public void generateMathNode_mathoid_strict() throws Exception {
        // mathml from mathoid and get the strict cmml semantics
        testConverterOnFile(CMMLHelper::getAbstractCmml, "mathnode4");
    }

    /**
     * Converts a MathNode into a an simplistic indented tree
     * representation of itself.
     *
     * @param node   Node to begin with and onwards for all children of it.
     * @param indent starting line used as an indent (e.g. start with "")
     * @return return a tree representation of itself
     */
    private String outprint(MathNode node, String indent) {
        StringBuilder sb = new StringBuilder(indent + node.toString() + "\n");
        node.getChildren().forEach(n -> sb.append(outprint(n, indent + " ")));
        return sb.toString();
    }

    private void testConverterOnFile(Function<String, Node> convert, String basicFilename) throws Exception {
        String expected = IOUtils.toString(this.getClass().getResourceAsStream(basicFilename + "_expected.txt"), "UTF-8");
        String mathml = IOUtils.toString(this.getClass().getResourceAsStream(basicFilename + "_test.txt"), "UTF-8");
        CMMLInfo cmmlInfo = new CMMLInfo(convert.apply(mathml));
        MathNode mathNode = MathNodeGenerator.generateMathNode(cmmlInfo.getFirstChild());

        Assert.assertThat(outprint(mathNode, ""), CoreMatchers.equalTo(expected));
    }

}