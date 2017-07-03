package com.formulasearchengine.mathmlsim.similarity.util;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Vincent Stange
 */
public class MathMLCanUtilTest {

    @Test
    public void canonicalize_real1() throws Exception {
        // prepare and execute
        String actualMathML = MathMLCanUtil.canonicalize(IOUtils.toString(this.getClass().getResourceAsStream("mathml_real_1_test.xml"), "UTF-8"));
        System.out.println(actualMathML);
    }

    @Test
    public void canonicalize() throws Exception {
        // prepare and execute
        String loadedMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_can_1_test.xml"), "UTF-8");
        String actualMathML = MathMLCanUtil.canonicalize(loadedMathML);
        String expectedMathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.1.m1.1\" alttext=\"\\EulerGamma@{z}=\\Int{0}{\\infty}@{t}{e^{-t}t^{z-1}}\">\n" +
                "    <semantics id=\"p1.1.m1.1a\">\n" +
                "        <mrow id=\"p1.1.m1.1.4\" xref=\"p1.1.m1.1.4.cmml\">\n" +
                "            \n" +
                "                \n" +
                "                    <mi id=\"p1.1.m1.1.1.2.1.2\" xref=\"p1.1.m1.1.1.2.1.1.cmml\">Γ</mi>\n" +
                "                \n" +
                "                \n" +
                "                \n" +
                "                    <mrow><mo id=\"p1.1.m1.1.1.5\" xref=\"p1.1.m1.1.1.1.cmml\">(</mo><mi id=\"p1.1.m1.1.1.6\" xref=\"p1.1.m1.1.1.6.cmml\">z</mi><mo id=\"p1.1.m1.1.1.7\" xref=\"p1.1.m1.1.1.1.cmml\">)</mo></mrow>\n" +
                "                    \n" +
                "                    \n" +
                "                \n" +
                "            \n" +
                "            <mo id=\"p1.1.m1.1.2\" xref=\"p1.1.m1.1.2.cmml\">=</mo>\n" +
                "            \n" +
                "                <msubsup id=\"p1.1.m1.1.3.2.1.6\" xref=\"p1.1.m1.1.3.2.1.1.cmml\">\n" +
                "                    <mo id=\"p1.1.m1.1.3.2.1.2\" xref=\"p1.1.m1.1.3.2.1.1.1.cmml\">∫</mo>\n" +
                "                    <mn id=\"p1.1.m1.1.3.2.1.3.1\" xref=\"p1.1.m1.1.3.2.1.3.1.cmml\">0</mn>\n" +
                "                    <mi id=\"p1.1.m1.1.3.2.1.4.1\" xref=\"p1.1.m1.1.3.2.1.4.1.cmml\">∞</mi>\n" +
                "                </msubsup>\n" +
                "                <mo id=\"p1.1.m1.1.3.8a\" xref=\"p1.1.m1.1.3.1.cmml\">\u2061</mo>\n" +
                "                \n" +
                "                    \n" +
                "                        \n" +
                "                            <msup id=\"p1.1.m1.1.3.4.6\" xref=\"p1.1.m1.1.3.4.6.cmml\">\n" +
                "                                <mi id=\"p1.1.m1.1.3.4.1\" xref=\"p1.1.m1.1.3.4.1.cmml\">e</mi>\n" +
                "                                <mrow id=\"p1.1.m1.1.3.4.2.1\" xref=\"p1.1.m1.1.3.4.2.1.cmml\">\n" +
                "                                    <mo id=\"p1.1.m1.1.3.4.2.1.1\" xref=\"p1.1.m1.1.3.4.2.1.1.cmml\">-</mo>\n" +
                "                                    <mi id=\"p1.1.m1.1.3.4.2.1.2\" xref=\"p1.1.m1.1.3.4.2.1.2.cmml\">t</mi>\n" +
                "                                </mrow>\n" +
                "                            </msup>\n" +
                "                            \n" +
                "                            <msup id=\"p1.1.m1.1.3.4.7\" xref=\"p1.1.m1.1.3.4.7.cmml\">\n" +
                "                                <mi id=\"p1.1.m1.1.3.4.3\" xref=\"p1.1.m1.1.3.4.3.cmml\">t</mi>\n" +
                "                                <mrow id=\"p1.1.m1.1.3.4.4.1\" xref=\"p1.1.m1.1.3.4.4.1.cmml\">\n" +
                "                                    <mi id=\"p1.1.m1.1.3.4.4.1.1\" xref=\"p1.1.m1.1.3.4.4.1.1.cmml\">z</mi>\n" +
                "                                    <mo id=\"p1.1.m1.1.3.4.4.1.2\" xref=\"p1.1.m1.1.3.4.4.1.2.cmml\">-</mo>\n" +
                "                                    <mn id=\"p1.1.m1.1.3.4.4.1.3\" xref=\"p1.1.m1.1.3.4.4.1.3.cmml\">1</mn>\n" +
                "                                </mrow>\n" +
                "                            </msup>\n" +
                "                        \n" +
                "                    \n" +
                "                    \n" +
                "                    <mi id=\"p1.1.m1.1.3.6\" xref=\"p1.1.m1.1.3.1.cmml\">d</mi>\n" +
                "                    \n" +
                "                    <mi id=\"p1.1.m1.1.3.7\" xref=\"p1.1.m1.1.3.7.cmml\">t</mi>\n" +
                "                \n" +
                "            \n" +
                "        </mrow>\n" +
                "        <annotation-xml encoding=\"MathML-Content\" id=\"p1.1.m1.1b\">\n" +
                "            <apply id=\"p1.1.m1.1.4.cmml\" xref=\"p1.1.m1.1.4\">\n" +
                "                <eq id=\"p1.1.m1.1.2.cmml\" xref=\"p1.1.m1.1.2\" />\n" +
                "                <apply id=\"p1.1.m1.1.1.1.cmml\" xref=\"p1.1.m1.1.1.8.2\">\n" +
                "                    <csymbol id=\"p1.1.m1.1.1.2.1.1.cmml\" xref=\"p1.1.m1.1.1.2.1.2\">Euler-Gamma</csymbol>\n" +
                "                    <ci id=\"p1.1.m1.1.1.6.cmml\" xref=\"p1.1.m1.1.1.6\">z</ci>\n" +
                "                </apply>\n" +
                "                <apply id=\"p1.1.m1.1.3.1.cmml\" xref=\"p1.1.m1.1.3.8\">\n" +
                "                    <apply id=\"p1.1.m1.1.3.2.1.1.cmml\" xref=\"p1.1.m1.1.3.2.1.6\">\n" +
                "                        <csymbol id=\"p1.1.m1.1.3.2.1.1.1.cmml\" xref=\"p1.1.m1.1.3.2.1.2\">semantic-definite-integral</csymbol>\n" +
                "                        <cn type=\"integer\" id=\"p1.1.m1.1.3.2.1.3.1.cmml\" xref=\"p1.1.m1.1.3.2.1.3.1\">0</cn>\n" +
                "                        <infinity id=\"p1.1.m1.1.3.2.1.4.1.cmml\" xref=\"p1.1.m1.1.3.2.1.4.1\" />\n" +
                "                    </apply>\n" +
                "                    <ci id=\"p1.1.m1.1.3.7.cmml\" xref=\"p1.1.m1.1.3.7\">t</ci>\n" +
                "                    <apply id=\"p1.1.m1.1.3.4.cmml\" xref=\"p1.1.m1.1.3.4\">\n" +
                "                        <times id=\"p1.1.m1.1.3.4.5.cmml\" xref=\"p1.1.m1.1.3.4.5\" />\n" +
                "                        <apply id=\"p1.1.m1.1.3.4.6.cmml\" xref=\"p1.1.m1.1.3.4.6\">\n" +
                "                            <power xmlns:m=\"http://www.w3.org/1998/Math/MathML\" xref=\"p1.1.m1.1.3.4.6\" />\n" +
                "                            <ci id=\"p1.1.m1.1.3.4.1.cmml\" xref=\"p1.1.m1.1.3.4.1\">e</ci>\n" +
                "                            <apply id=\"p1.1.m1.1.3.4.2.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1\">\n" +
                "                                <minus id=\"p1.1.m1.1.3.4.2.1.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1.1\" />\n" +
                "                                <ci id=\"p1.1.m1.1.3.4.2.1.2.cmml\" xref=\"p1.1.m1.1.3.4.2.1.2\">t</ci>\n" +
                "                            </apply>\n" +
                "                        </apply>\n" +
                "                        <apply id=\"p1.1.m1.1.3.4.7.cmml\" xref=\"p1.1.m1.1.3.4.7\">\n" +
                "                            <power xmlns:m=\"http://www.w3.org/1998/Math/MathML\" xref=\"p1.1.m1.1.3.4.7\" />\n" +
                "                            <ci id=\"p1.1.m1.1.3.4.3.cmml\" xref=\"p1.1.m1.1.3.4.3\">t</ci>\n" +
                "                            <apply id=\"p1.1.m1.1.3.4.4.1.cmml\" xref=\"p1.1.m1.1.3.4.4.1\">\n" +
                "                                <minus id=\"p1.1.m1.1.3.4.4.1.2.cmml\" xref=\"p1.1.m1.1.3.4.4.1.2\" />\n" +
                "                                <ci id=\"p1.1.m1.1.3.4.4.1.1.cmml\" xref=\"p1.1.m1.1.3.4.4.1.1\">z</ci>\n" +
                "                                <cn type=\"integer\" id=\"p1.1.m1.1.3.4.4.1.3.cmml\" xref=\"p1.1.m1.1.3.4.4.1.3\">1</cn>\n" +
                "                            </apply>\n" +
                "                        </apply>\n" +
                "                    </apply>\n" +
                "                </apply>\n" +
                "            </apply>\n" +
                "        </annotation-xml>\n" +
                "    </semantics>\n" +
                "</math>\n";
        // validate
        assertThat(actualMathML, is(expectedMathML));
    }

    /**
     * Check for UnaryOperatorRemover - this module should not be applied!
     */
    @Test
    public void canonicalize_error_1() throws Exception {
        // prepare and execute
        String loadedMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_error_example_1.xml"), "UTF-8");
        String actualMathML = MathMLCanUtil.canonicalize(loadedMathML);
        String expected = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.1.m1.1\">\n" +
                "    <semantics id=\"p1.1.m1.1a\">\n" +
                "        <mrow id=\"p1.1.m1.1.3.4.2.1\" xref=\"p1.1.m1.1.3.4.2.1.cmml\">\n" +
                "            <mo id=\"p1.1.m1.1.3.4.2.1.1\" xref=\"p1.1.m1.1.3.4.2.1.1.cmml\">-</mo>\n" +
                "            <mi id=\"p1.1.m1.1.3.4.2.1.2\" xref=\"p1.1.m1.1.3.4.2.1.2.cmml\">t</mi>\n" +
                "        </mrow>\n" +
                "        <annotation-xml encoding=\"MathML-Content\" id=\"p1.1.m1.1b\">\n" +
                "            <apply id=\"p1.1.m1.1.3.4.2.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1\">\n" +
                "                <minus id=\"p1.1.m1.1.3.4.2.1.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1.1\" />\n" +
                "                <ci id=\"p1.1.m1.1.3.4.2.1.2.cmml\" xref=\"p1.1.m1.1.3.4.2.1.2\">t</ci>\n" +
                "            </apply>\n" +
                "        </annotation-xml>\n" +
                "    </semantics>\n" +
                "</math>\n";
        // they should be equal - if not, the UnaryOperatorRemover is active and this should not be!
        assertThat(actualMathML, is(expected));
    }

}