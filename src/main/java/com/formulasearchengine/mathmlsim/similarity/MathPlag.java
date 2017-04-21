package com.formulasearchengine.mathmlsim.similarity;

import com.formulasearchengine.mathmlsim.similarity.result.Match;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is still in progress.
 * In its current form it will be used for the VMEXT demo.
 *
 * @author Vincent Stange
 */
public class MathPlag {

    public static List<Match> compareMathML(String mathml1, String mathml2) {
        try {
            return compareDocuments(new CMMLInfo(mathml1), new CMMLInfo(mathml2));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Old score - this is basically a print out for comparison and be deleted later on
     *
     * @return Map of all found factors
     */
    public static Map<String, Object> compareOriginalFactors(String mathml1, String mathml2) {
        try {
            CMMLInfo refDoc = new CMMLInfo(mathml1);
            CMMLInfo compDoc = new CMMLInfo(mathml2);
            final Integer depth = compDoc.getDepth(refDoc.getXQuery());
            final Double coverage = compDoc.getCoverage(refDoc.getElements());
            Boolean match = compDoc.toStrictCmml().isMatch(refDoc.toStrictCmml().getXQuery());

            HashMap<String, Object> result = new HashMap<>();
            result.put("depth", depth);
            result.put("coverage", coverage);
            result.put("structure match", match);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * New score -TODO
     *
     * @param refDoc
     * @param compDoc
     * @return
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    static List<Match> compareDocuments(CMMLInfo refDoc, CMMLInfo compDoc) throws TransformerException, ParserConfigurationException {
        // Find matches with new scores
        return new SubTreeComparison("identical").getSimilarities(refDoc, compDoc, true);
    }


}
