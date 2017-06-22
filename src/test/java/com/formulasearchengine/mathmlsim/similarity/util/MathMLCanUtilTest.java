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
    public void canonicalize() throws Exception {
        // prepare and execute
        String loadedMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_can_1_test.xml"), "UTF-8");
        String actualMathML = MathMLCanUtil.canonicalize(loadedMathML);
        String expectedMathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.1.m1.1\" alttext=\"\\EulerGamma@{z}=\\Int{0}{\\infty}@{t}{e^{-t}t^{z-1}}\">\r\n" +
                "    <semantics id=\"p1.1.m1.1a\">\r\n" +
                "        <mrow id=\"p1.1.m1.1.4\" xref=\"p1.1.m1.1.4.cmml\">\r\n" +
                "            \r\n" +
                "                \r\n" +
                "                    <mi id=\"p1.1.m1.1.1.2.1.2\" xref=\"p1.1.m1.1.1.2.1.1.cmml\">Γ</mi>\r\n" +
                "                \r\n" +
                "                \r\n" +
                "                \r\n" +
                "                    <mrow><mo id=\"p1.1.m1.1.1.5\" xref=\"p1.1.m1.1.1.1.cmml\">(</mo><mi id=\"p1.1.m1.1.1.6\" xref=\"p1.1.m1.1.1.6.cmml\">z</mi><mo id=\"p1.1.m1.1.1.7\" xref=\"p1.1.m1.1.1.1.cmml\">)</mo></mrow>\r\n" +
                "                    \r\n" +
                "                    \r\n" +
                "                \r\n" +
                "            \r\n" +
                "            <mo id=\"p1.1.m1.1.2\" xref=\"p1.1.m1.1.2.cmml\">=</mo>\r\n" +
                "            \r\n" +
                "                <msubsup id=\"p1.1.m1.1.3.2.1.6\" xref=\"p1.1.m1.1.3.2.1.1.cmml\">\r\n" +
                "                    <mo id=\"p1.1.m1.1.3.2.1.2\" xref=\"p1.1.m1.1.3.2.1.1.1.cmml\">∫</mo>\r\n" +
                "                    <mn id=\"p1.1.m1.1.3.2.1.3.1\" xref=\"p1.1.m1.1.3.2.1.3.1.cmml\">0</mn>\r\n" +
                "                    <mi id=\"p1.1.m1.1.3.2.1.4.1\" xref=\"p1.1.m1.1.3.2.1.4.1.cmml\">∞</mi>\r\n" +
                "                </msubsup>\r\n" +
                "                <mo id=\"p1.1.m1.1.3.8a\" xref=\"p1.1.m1.1.3.1.cmml\">\u2061</mo>\r\n" +
                "                \r\n" +
                "                    \r\n" +
                "                        \r\n" +
                "                            <msup id=\"p1.1.m1.1.3.4.6\" xref=\"p1.1.m1.1.3.4.6.cmml\">\r\n" +
                "                                <mi id=\"p1.1.m1.1.3.4.1\" xref=\"p1.1.m1.1.3.4.1.cmml\">e</mi>\r\n" +
                "                                <mrow id=\"p1.1.m1.1.3.4.2.1\" xref=\"p1.1.m1.1.3.4.2.1.cmml\">\r\n" +
                "                                    <mo id=\"p1.1.m1.1.3.4.2.1.1\" xref=\"p1.1.m1.1.3.4.2.1.1.cmml\">-</mo>\r\n" +
                "                                    <mi id=\"p1.1.m1.1.3.4.2.1.2\" xref=\"p1.1.m1.1.3.4.2.1.2.cmml\">t</mi>\r\n" +
                "                                </mrow>\r\n" +
                "                            </msup>\r\n" +
                "                            \r\n" +
                "                            <msup id=\"p1.1.m1.1.3.4.7\" xref=\"p1.1.m1.1.3.4.7.cmml\">\r\n" +
                "                                <mi id=\"p1.1.m1.1.3.4.3\" xref=\"p1.1.m1.1.3.4.3.cmml\">t</mi>\r\n" +
                "                                <mrow id=\"p1.1.m1.1.3.4.4.1\" xref=\"p1.1.m1.1.3.4.4.1.cmml\">\r\n" +
                "                                    <mi id=\"p1.1.m1.1.3.4.4.1.1\" xref=\"p1.1.m1.1.3.4.4.1.1.cmml\">z</mi>\r\n" +
                "                                    <mo id=\"p1.1.m1.1.3.4.4.1.2\" xref=\"p1.1.m1.1.3.4.4.1.2.cmml\">-</mo>\r\n" +
                "                                    <mn id=\"p1.1.m1.1.3.4.4.1.3\" xref=\"p1.1.m1.1.3.4.4.1.3.cmml\">1</mn>\r\n" +
                "                                </mrow>\r\n" +
                "                            </msup>\r\n" +
                "                        \r\n" +
                "                    \r\n" +
                "                    \r\n" +
                "                    <mi id=\"p1.1.m1.1.3.6\" xref=\"p1.1.m1.1.3.1.cmml\">d</mi>\r\n" +
                "                    \r\n" +
                "                    <mi id=\"p1.1.m1.1.3.7\" xref=\"p1.1.m1.1.3.7.cmml\">t</mi>\r\n" +
                "                \r\n" +
                "            \r\n" +
                "        </mrow>\r\n" +
                "        <annotation-xml encoding=\"MathML-Content\" id=\"p1.1.m1.1b\">\r\n" +
                "            <apply id=\"p1.1.m1.1.4.cmml\" xref=\"p1.1.m1.1.4\">\r\n" +
                "                <eq id=\"p1.1.m1.1.2.cmml\" xref=\"p1.1.m1.1.2\" />\r\n" +
                "                <apply id=\"p1.1.m1.1.1.1.cmml\" xref=\"p1.1.m1.1.1.8.2\">\r\n" +
                "                    <csymbol id=\"p1.1.m1.1.1.2.1.1.cmml\" xref=\"p1.1.m1.1.1.2.1.2\">Euler-Gamma</csymbol>\r\n" +
                "                    <ci id=\"p1.1.m1.1.1.6.cmml\" xref=\"p1.1.m1.1.1.6\">z</ci>\r\n" +
                "                </apply>\r\n" +
                "                <apply id=\"p1.1.m1.1.3.1.cmml\" xref=\"p1.1.m1.1.3.8\">\r\n" +
                "                    <apply id=\"p1.1.m1.1.3.2.1.1.cmml\" xref=\"p1.1.m1.1.3.2.1.6\">\r\n" +
                "                        <csymbol id=\"p1.1.m1.1.3.2.1.1.1.cmml\" xref=\"p1.1.m1.1.3.2.1.2\">semantic-definite-integral</csymbol>\r\n" +
                "                        <cn type=\"integer\" id=\"p1.1.m1.1.3.2.1.3.1.cmml\" xref=\"p1.1.m1.1.3.2.1.3.1\">0</cn>\r\n" +
                "                        <infinity id=\"p1.1.m1.1.3.2.1.4.1.cmml\" xref=\"p1.1.m1.1.3.2.1.4.1\" />\r\n" +
                "                    </apply>\r\n" +
                "                    <ci id=\"p1.1.m1.1.3.7.cmml\" xref=\"p1.1.m1.1.3.7\">t</ci>\r\n" +
                "                    <apply id=\"p1.1.m1.1.3.4.cmml\" xref=\"p1.1.m1.1.3.4\">\r\n" +
                "                        <times id=\"p1.1.m1.1.3.4.5.cmml\" xref=\"p1.1.m1.1.3.4.5\" />\r\n" +
                "                        <apply id=\"p1.1.m1.1.3.4.6.cmml\" xref=\"p1.1.m1.1.3.4.6\">\r\n" +
                "                            <power xmlns:m=\"http://www.w3.org/1998/Math/MathML\" xref=\"p1.1.m1.1.3.4.6\" />\r\n" +
                "                            <ci id=\"p1.1.m1.1.3.4.1.cmml\" xref=\"p1.1.m1.1.3.4.1\">e</ci>\r\n" +
                "                            <apply id=\"p1.1.m1.1.3.4.2.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1\">\r\n" +
                "                                <minus id=\"p1.1.m1.1.3.4.2.1.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1.1\" />\r\n" +
                "                                <ci id=\"p1.1.m1.1.3.4.2.1.2.cmml\" xref=\"p1.1.m1.1.3.4.2.1.2\">t</ci>\r\n" +
                "                            </apply>\r\n" +
                "                        </apply>\r\n" +
                "                        <apply id=\"p1.1.m1.1.3.4.7.cmml\" xref=\"p1.1.m1.1.3.4.7\">\r\n" +
                "                            <power xmlns:m=\"http://www.w3.org/1998/Math/MathML\" xref=\"p1.1.m1.1.3.4.7\" />\r\n" +
                "                            <ci id=\"p1.1.m1.1.3.4.3.cmml\" xref=\"p1.1.m1.1.3.4.3\">t</ci>\r\n" +
                "                            <apply id=\"p1.1.m1.1.3.4.4.1.cmml\" xref=\"p1.1.m1.1.3.4.4.1\">\r\n" +
                "                                <minus id=\"p1.1.m1.1.3.4.4.1.2.cmml\" xref=\"p1.1.m1.1.3.4.4.1.2\" />\r\n" +
                "                                <ci id=\"p1.1.m1.1.3.4.4.1.1.cmml\" xref=\"p1.1.m1.1.3.4.4.1.1\">z</ci>\r\n" +
                "                                <cn type=\"integer\" id=\"p1.1.m1.1.3.4.4.1.3.cmml\" xref=\"p1.1.m1.1.3.4.4.1.3\">1</cn>\r\n" +
                "                            </apply>\r\n" +
                "                        </apply>\r\n" +
                "                    </apply>\r\n" +
                "                </apply>\r\n" +
                "            </apply>\r\n" +
                "        </annotation-xml>\r\n" +
                "    </semantics>\r\n" +
                "</math>\r\n";
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
        String expected = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.1.m1.1\">\r\n" +
                "    <semantics id=\"p1.1.m1.1a\">\r\n" +
                "        <mrow id=\"p1.1.m1.1.3.4.2.1\" xref=\"p1.1.m1.1.3.4.2.1.cmml\">\r\n" +
                "            <mo id=\"p1.1.m1.1.3.4.2.1.1\" xref=\"p1.1.m1.1.3.4.2.1.1.cmml\">-</mo>\r\n" +
                "            <mi id=\"p1.1.m1.1.3.4.2.1.2\" xref=\"p1.1.m1.1.3.4.2.1.2.cmml\">t</mi>\r\n" +
                "        </mrow>\r\n" +
                "        <annotation-xml encoding=\"MathML-Content\" id=\"p1.1.m1.1b\">\r\n" +
                "            <apply id=\"p1.1.m1.1.3.4.2.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1\">\r\n" +
                "                <minus id=\"p1.1.m1.1.3.4.2.1.1.cmml\" xref=\"p1.1.m1.1.3.4.2.1.1\" />\r\n" +
                "                <ci id=\"p1.1.m1.1.3.4.2.1.2.cmml\" xref=\"p1.1.m1.1.3.4.2.1.2\">t</ci>\r\n" +
                "            </apply>\r\n" +
                "        </annotation-xml>\r\n" +
                "    </semantics>\r\n" +
                "</math>\r\n";
        // they should be equal - if not, the UnaryOperatorRemover is active and this should not be!
        assertThat(actualMathML, is(expected));
    }

}