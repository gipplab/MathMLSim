package com.formulasearchengine.mathmlsim.similarity.node;

import com.formulasearchengine.mathmlsim.similarity.util.CMMLHelper;
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
        testConverterOnFile(CMMLHelper::getCmml, "mathnode1", false);
    }

    @Test
    public void generateMathNode_strict() throws Exception {
        // mathml from latexml and get the strict cmml semantics
        testConverterOnFile(CMMLHelper::getStrictCmml, "mathnode2", true);
    }

    @Test
    public void generateMathNode_mathoid_normal() throws Exception {
        // mathml from mathoid and get the cmml semantics
        testConverterOnFile(CMMLHelper::getCmml, "mathnode3", false);
    }

    @Test
    public void generateMathNode_mathoid_strict() throws Exception {
        // mathml from mathoid and get the strict cmml semantics
        testConverterOnFile(CMMLHelper::getStrictCmml, "mathnode4", true);
    }

    private void testConverterOnFile(Function<String, Node> convert, String basicFilename, boolean checkAbstract) throws Exception {
        String expected = IOUtils.toString(this.getClass().getResourceAsStream(basicFilename + "_expected.txt"), "UTF-8");
        String mathml = IOUtils.toString(this.getClass().getResourceAsStream(basicFilename + "_test.txt"), "UTF-8");
        MathNode mathNode = MathNodeGenerator.generateMathNode(convert.apply(mathml));

        if (checkAbstract)
            mathNode = MathNodeGenerator.toAbstract(mathNode);

        Assert.assertThat(MathNodeGenerator.print(mathNode, ""), CoreMatchers.equalTo(expected));
    }

}