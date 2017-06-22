package com.formulasearchengine.mathmlsim.similarity;

import com.formulasearchengine.mathmlsim.similarity.result.Match;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Vincent Stange
 */
public class MathPlagTest {

    @Test
    public void complexRun_all() throws IOException, ParserConfigurationException, TransformerException {
        // prepare two mathml files, cmml is in the annotate element
        String refMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_complex_1.xml"), "UTF-8");
        String compMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_complex_2.xml"), "UTF-8");

        // test the old comparison results
        Map<String, Object> result = MathPlag.compareOriginalFactors(refMathML, compMathML);
        assertThat(result, notNullValue());
        assertThat(result.get("coverage"), is(1.0));
        assertThat(result.get("depth"), nullValue());
        assertThat(result.get("structure match"), is(true));
        assertThat(result.get("formula"), is(true));

        // test the identical mathplag results
        List<Match> identMatch = MathPlag.compareIdenticalMathML(refMathML, compMathML);
        assertThat(identMatch, notNullValue());
        assertThat(identMatch.size(), is(3));

        // test the similar mathplag results
        List<Match> simMatch = MathPlag.compareSimilarMathML(refMathML, compMathML);
        assertThat(simMatch, notNullValue());
        assertThat(simMatch.size(), is(1));
        assertThat(simMatch.get(0).id, is("p1.1.m1.1.4.cmml"));
        // The query should be completely found inside the comparisonMathML, depth is therefor = 0
        assertThat(simMatch.get(0).depth, is(0)); // depth in ref tree
        assertThat(simMatch.get(0).matches.get(0).depth, is(0)); // depth in comp tree
    }

    @Test
    public void simpleRun_identical_1() throws IOException, ParserConfigurationException, TransformerException {
        // prepare two mathml files, cmml is in the annotate element
        String refMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_annotation_1.xml"), "UTF-8");
        String compMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_annotation_2.xml"), "UTF-8");

        // test the old comparison results
        Map<String, Object> result = MathPlag.compareOriginalFactors(refMathML, compMathML);
        assertThat(result, notNullValue());
        assertThat(result.get("coverage"), is(1.0));
        assertThat(result.get("depth"), is(5));
        assertThat(result.get("structure match"), is(true));
        assertThat(result.get("formula"), is(false));

        // test the mathplag results
        List<Match> matches = MathPlag.compareIdenticalMathML(refMathML, compMathML);
        assertThat(matches, notNullValue());
        assertThat(matches.size(), is(1));
        assertThat(matches.get(0).id, is("p1.1.m1.1.4.cmml"));
        assertThat(matches.get(0).coverage, is(1.0));
    }

    @Test
    public void simpleRun_similar_1() throws IOException, ParserConfigurationException, TransformerException {
        // prepare two mathml files, cmml is in the annotate element
        String refMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_query_pure_1.xml"), "UTF-8");
        String compMathML = IOUtils.toString(this.getClass().getResourceAsStream("mathml_annotation_3.xml"), "UTF-8");
        // test the old comparison results
        Map<String, Object> result = MathPlag.compareOriginalFactors(refMathML, compMathML);
        assertThat(result, notNullValue());
        assertThat(result.get("coverage"), is(0.0));
        assertThat(result.get("depth"), nullValue());
        assertThat(result.get("structure match"), is(true));
        assertThat(result.get("formula"), is(true));

        // test the identical mathplag results
        List<Match> identMatch = MathPlag.compareIdenticalMathML(refMathML, compMathML);
        assertThat(identMatch, notNullValue());
        assertThat(identMatch.size(), is(0));

        // test the similar mathplag results
        List<Match> simMatch = MathPlag.compareSimilarMathML(refMathML, compMathML);
        assertThat(simMatch, notNullValue());
        assertThat(simMatch.size(), is(1));
        // the query has no id attribute for the qvar element
        assertThat(simMatch.get(0).id, nullValue());
        // The query should be completely found inside the comparisonMathML, depth is therefor = 1
        assertThat(simMatch.get(0).depth, is(1));
        assertThat(simMatch.get(0).matches.get(0).depth, is(1));

    }

}