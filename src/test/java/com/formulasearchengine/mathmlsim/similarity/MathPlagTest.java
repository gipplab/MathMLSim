package com.formulasearchengine.mathmlsim.similarity;

import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * TODO
 *
 * @author Vincent Stange
 */
public class MathPlagTest {

    @Test
    public void foobar() throws IOException, ParserConfigurationException, TransformerException {
        String mathml1 = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.1.m1.1\" class=\"ltx_Math\" alttext=\"a+b\" display=\"inline\">\n" +
                "  <semantics id=\"p1.1.m1.1a\">\n" +
                "    <mrow id=\"p1.1.m1.1.4\" xref=\"p1.1.m1.1.4.cmml\">\n" +
                "      <mi id=\"p1.1.m1.1.1\" xref=\"p1.1.m1.1.1.cmml\">a</mi>\n" +
                "      <mo id=\"p1.1.m1.1.2\" xref=\"p1.1.m1.1.2.cmml\">+</mo>\n" +
                "      <mi id=\"p1.1.m1.1.3\" xref=\"p1.1.m1.1.3.cmml\">b</mi>\n" +
                "    </mrow>\n" +
                "    <annotation-xml encoding=\"MathML-Content\" id=\"p1.1.m1.1b\">\n" +
                "      <apply id=\"p1.1.m1.1.4.cmml\" xref=\"p1.1.m1.1.4\">\n" +
                "        <plus id=\"p1.1.m1.1.2.cmml\" xref=\"p1.1.m1.1.2\"/>\n" +
                "        <ci id=\"p1.1.m1.1.1.cmml\" xref=\"p1.1.m1.1.1\">a</ci>\n" +
                "        <ci id=\"p1.1.m1.1.3.cmml\" xref=\"p1.1.m1.1.3\">b</ci>\n" +
                "      </apply>\n" +
                "    </annotation-xml>\n" +
                "    <annotation encoding=\"application/x-tex\" id=\"p1.1.m1.1c\">a+b</annotation>\n" +
                "  </semantics>\n" +
                "</math>\n";

        String mathml2 = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.1.m1.1\" class=\"ltx_Math\" alttext=\"(a+b)*c\" display=\"inline\">\n" +
                "  <semantics id=\"p1.1.m1.1a\">\n" +
                "    <mrow id=\"p1.1.m1.1.8\" xref=\"p1.1.m1.1.8.cmml\">\n" +
                "      <mrow id=\"p1.1.m1.1.8.1b\">\n" +
                "        <mo stretchy=\"false\" id=\"p1.1.m1.1.8.1\">(</mo>\n" +
                "        <mrow id=\"XM1\" xref=\"XM1.cmml\">\n" +
                "          <mi id=\"p1.1.m1.1.2\" xref=\"p1.1.m1.1.2.cmml\">a</mi>\n" +
                "          <mo id=\"p1.1.m1.1.3\" xref=\"p1.1.m1.1.3.cmml\">+</mo>\n" +
                "          <mi id=\"p1.1.m1.1.4\" xref=\"p1.1.m1.1.4.cmml\">b</mi>\n" +
                "        </mrow>\n" +
                "        <mo stretchy=\"false\" id=\"p1.1.m1.1.8.1a\">)</mo>\n" +
                "      </mrow>\n" +
                "      <mo id=\"p1.1.m1.1.6\" xref=\"p1.1.m1.1.6.cmml\">*</mo>\n" +
                "      <mi id=\"p1.1.m1.1.7\" xref=\"p1.1.m1.1.7.cmml\">c</mi>\n" +
                "    </mrow>\n" +
                "    <annotation-xml encoding=\"MathML-Content\" id=\"p1.1.m1.1b\">\n" +
                "      <apply id=\"p1.1.m1.1.8.cmml\" xref=\"p1.1.m1.1.8\">\n" +
                "        <times id=\"p1.1.m1.1.6.cmml\" xref=\"p1.1.m1.1.6\"/>\n" +
                "        <apply id=\"XM1.cmml\" xref=\"XM1\">\n" +
                "          <plus id=\"p1.1.m1.1.3.cmml\" xref=\"p1.1.m1.1.3\"/>\n" +
                "          <ci id=\"p1.1.m1.1.2.cmml\" xref=\"p1.1.m1.1.2\">a</ci>\n" +
                "          <ci id=\"p1.1.m1.1.4.cmml\" xref=\"p1.1.m1.1.4\">b</ci>\n" +
                "        </apply>\n" +
                "        <ci id=\"p1.1.m1.1.7.cmml\" xref=\"p1.1.m1.1.7\">c</ci>\n" +
                "      </apply>\n" +
                "    </annotation-xml>\n" +
                "    <annotation encoding=\"application/x-tex\" id=\"p1.1.m1.1c\">(a+b)*c</annotation>\n" +
                "  </semantics>\n" +
                "</math>\n";

        MathPlag.compareMathML(mathml1, mathml2);
    }

}