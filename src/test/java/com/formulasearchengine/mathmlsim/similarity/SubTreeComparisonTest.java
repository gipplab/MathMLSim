package com.formulasearchengine.mathmlsim.similarity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formulasearchengine.mathmlsim.similarity.node.MathNode;
import com.formulasearchengine.mathmlsim.similarity.result.Match;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.junit.Assert.*;

/**
 * Test class for our tree comparison algorithm
 *
 * @author Vincent Stange
 */
public class SubTreeComparisonTest {

    @Test
    public void getSimilarities() throws Exception {
        /*
        \EulerGamma@{z}=\Int{0}{\infty}@{t}{e^{-t}t^{z-1}}
        \EulerGamma@{z}=\Int{0}{\infty}@{x}{e^{-x}t^{z-1}}
        I expect 3 similarities:
        1. \EulerGamma@{z}
        2. t^{z-1}
        3. \Int{0} (in CMML this also is a tree branch on its own)
         */
        testSimilarities("full_sim1");
    }

    private void testSimilarities(String basicFilename) throws Exception {
        String mathmls = IOUtils.toString(this.getClass().getResourceAsStream(basicFilename + "_test.txt"), "UTF-8");
        String[] split = mathmls.split("@@@"); // I use this as a simple split between two mathml's
        CMMLInfo doc1 = new CMMLInfo(split[0].trim());
        CMMLInfo doc2 = new CMMLInfo(split[1].trim());

        // run the comparison
        List<Match> found = new SubTreeComparison("identical").getSimilarities(doc1, doc2, true);
        // - and write the similarities as a string
        String actual = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(found);

        // valide the similarities as a JSON string
        String expected = IOUtils.toString(this.getClass().getResourceAsStream(basicFilename + "_expected.txt"), "UTF-8");
        Assert.assertThat(actual, equalToIgnoringWhiteSpace(expected));
    }

    @Test
    public void findSimilarities() throws Exception {

    }

    @Test
    public void isIdenticalTree_positive() throws Exception {
        MathNode apply = new MathNode("apply", "");
        apply.addChild(new MathNode("minus", null));
        apply.addChild(new MathNode("x", null));
        apply.addChild(new MathNode("y", null));

        // x-y = x-y
        assertTrue(new SubTreeComparison("identical").isIdenticalTree(apply, apply));
    }

    @Test
    public void isIdenticalTree_positive_strict() throws Exception {
        MathNode apply = new MathNode("apply", "arith1").setAbstractNode();
        ;
        apply.addChild(new MathNode("arith1", "plus").setAbstractNode());
        apply.addChild(new MathNode("ci", "x").setAbstractNode());
        apply.addChild(new MathNode("cn", "1").setAbstractNode());

        MathNode apply2 = new MathNode("apply", "arith1").setAbstractNode();
        apply2.addChild(new MathNode("arith1", "minus").setAbstractNode());
        apply2.addChild(new MathNode("ci", "y").setAbstractNode());
        apply2.addChild(new MathNode("cn", "2").setAbstractNode());

        // x+1 = y-2 - they have the same structure and the nodes are marked strict - this should be equal
        assertTrue(new SubTreeComparison("identical").isIdenticalTree(apply, apply2));
    }

    @Test
    public void isIdenticalTree_positive_order_insensitive() throws Exception {
        MathNode apply1 = new MathNode("apply", "");
        apply1.addChild(new MathNode("plus", null));
        apply1.addChild(new MathNode("x", null));
        apply1.addChild(new MathNode("y", null));

        MathNode apply2 = new MathNode("apply", "");
        apply2.addChild(new MathNode("plus", null));
        apply2.addChild(new MathNode("y", null));
        apply2.addChild(new MathNode("x", null));

        // x+y = y+x
        assertTrue(new SubTreeComparison("identical").isIdenticalTree(apply1, apply2));
    }

    @Test
    public void isIdenticalTree_negative() throws Exception {
        MathNode apply1 = new MathNode("apply", "");
        apply1.addChild(new MathNode("plus", null));
        apply1.addChild(new MathNode("x", null));
        apply1.addChild(new MathNode("y", null));

        MathNode apply2 = new MathNode("apply", "");
        apply2.addChild(new MathNode("plus", null));
        apply2.addChild(new MathNode("x", null));
        apply2.addChild(new MathNode("z", null));

        // x+y != x+z
        assertFalse(new SubTreeComparison("identical").isIdenticalTree(apply1, apply2));
    }

    @Test
    public void filterSameChildren() throws Exception {
        SubTreeComparison similar = new SubTreeComparison("similar");
        // prepare a list of children from an apply node
        List<MathNode> list = new ArrayList<>();
        list.add(new MathNode("plus", null));
        list.add(new MathNode("x", null));
        list.add(new MathNode("x", null));
        // execute
        List<MathNode> findings = similar.filterSameChildren(new MathNode("x", null), list);
        // should find both x
        assertThat(findings.size(), is(2));
        assertThat(findings.get(0), is(new MathNode("x", null)));
    }

    @Test
    public void getCoverage_identical() throws Exception {
        // frist list of leafs from a tree
        List<MathNode> list1 = new ArrayList<>();
        list1.add(new MathNode("ci", "x"));
        list1.add(new MathNode("cn", "3"));

        List<MathNode> list2 = new ArrayList<>();
        list2.add(new MathNode("ci", "x"));
        list2.add(new MathNode("cn", "2"));

        assertThat(SubTreeComparison.getCoverage(list1, list2), is(0.5));
    }

    @Test
    public void getCoverage_similar() throws Exception {
        // frist list of leafs from a tree
        List<MathNode> list1 = new ArrayList<>();
        list1.add(new MathNode("ci", "x").setAbstractNode());
        list1.add(new MathNode("cn", "3").setAbstractNode());

        List<MathNode> list2 = new ArrayList<>();
        list2.add(new MathNode("ci", "x").setAbstractNode());
        list2.add(new MathNode("cn", "2").setAbstractNode());

        assertThat(SubTreeComparison.getCoverage(list1, list2), is(0.5));
    }

}