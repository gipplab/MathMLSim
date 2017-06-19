package com.formulasearchengine.mathmlsim.similarity;

import com.formulasearchengine.mathmlsim.similarity.result.Match;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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

    /**
     * Compare two CMMLInfo document against each other. The type defines how a Match will be displayed.
     *
     * @param mathml1 Reference MathML string (must contain pMML and cMML)
     * @param mathml2 Comparison MathML string (must contain pMML and cMML)
     * @param type    defines how a Match will be displayed (similar / identical)
     * @return list of matches / similarities, list can be empty.
     */
    public static List<Match> compareMathML(String mathml1, String mathml2, String type) throws IOException, ParserConfigurationException {
        return compareDocuments(new CMMLInfo(mathml1), new CMMLInfo(mathml2), type);
    }

    /**
     * Compare two CMMLInfo document against each other. The type defines how a Match will be displayed.
     *
     * @param refDoc  Reference CMMLInfo document
     * @param compDoc Comparison CMMLInfo document
     * @param type    defines how a Match will be displayed (similar / identical)
     * @return list of matches / similarities, list can be empty but never null
     */
    static List<Match> compareDocuments(CMMLInfo refDoc, CMMLInfo compDoc, String type) {
        // Find matches between them. Single leafs are (e.g. a single variable) will not be considered
        return new SubTreeComparison(type).getSimilarities(refDoc, compDoc, true);
    }

    /**
     * Old score - this is basically a print out for comparison and be deleted later on
     *
     * @param refMathML Reference MathML string (must contain pMML and cMML)
     * @param compMathML Comparison MathML string (must contain pMML and cMML)
     * @return ap of all found factors
     */
    public static Map<String, Object> compareOriginalFactors(String refMathML, String compMathML) {
        try {
            CMMLInfo refDoc = new CMMLInfo(refMathML);
            CMMLInfo compDoc = new CMMLInfo(compMathML);
            final Integer depth = compDoc.getDepth(refDoc.getXQuery());
            final Double coverage = compDoc.getCoverage(refDoc.getElements());
            Boolean formula = compDoc.isEquation();
            Boolean structMatch = compDoc.toStrictCmml().abstract2CDs()
                    .isMatch(refDoc.toStrictCmml().abstract2CDs().getXQuery());
            Boolean dataMatch = new CMMLInfo(compMathML).toDataCmml().abstract2DTs()
                    .isMatch(new CMMLInfo(refMathML).abstract2DTs().getXQuery());

            HashMap<String, Object> result = new HashMap<>();
            result.put("depth", depth);
            result.put("coverage", coverage);
            result.put("structure match", structMatch);
            result.put("data match", dataMatch);
            result.put("formula", formula);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
