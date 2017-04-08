package com.formulasearchengine.mathmlsim;

import com.formulasearchengine.mathmlsim.distances.earthmover.EarthMoverDistanceWrapper;
import com.formulasearchengine.mathmlsim.distances.earthmover.JFastEMD;
import com.formulasearchengine.mathmlsim.distances.earthmover.Signature;
import com.formulasearchengine.mathmltools.mml.CMMLInfo;
import com.formulasearchengine.mathmltools.xmlhelper.XMLHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Felix Hamborg <felixhamborg@gmail.com> on 05.12.16.
 */
public class Distances {

    private static final Log LOG = LogFactory.getLog(Distances.class);

    private static final java.text.DecimalFormat DECIMAL_FORMAT = new java.text.DecimalFormat("#.###");

    /**
     * probably only makes sense to compute this on CI
     *
     * @param h1
     * @param h2
     * @return
     */
    public static double computeEarthMoverAbsoluteDistance(java.util.Map<String, Integer> h1, java.util.Map<String, Integer> h2) {
        Signature s1 = EarthMoverDistanceWrapper.histogramToSignature(h1);
        Signature s2 = EarthMoverDistanceWrapper.histogramToSignature(h2);

        return JFastEMD.distance(s1, s2, 0.0);
    }

    public static double computeRelativeDistance(java.util.Map<String, Integer> h1, java.util.Map<String, Integer> h2) {
        final java.util.Set<String> keySet = new java.util.HashSet();
        keySet.addAll(h1.keySet());
        keySet.addAll(h2.keySet());
        final double numberOfUniqueElements = keySet.size();
        if (numberOfUniqueElements == 0.0) {
            return 0.0;
        }

        final double absoluteDistance = computeAbsoluteDistance(h1, h2);

        return absoluteDistance / numberOfUniqueElements;
    }


    /**
     * compares two histograms and returns the accumulated number of differences (absolute)
     *
     * @param h1
     * @param h2
     * @return
     */
    public static double computeAbsoluteDistance(java.util.Map<String, Integer> h1, java.util.Map<String, Integer> h2) {
        double distance = 0;

        final java.util.Set<String> keySet = new java.util.HashSet();
        keySet.addAll(h1.keySet());
        keySet.addAll(h2.keySet());

        for (String key : keySet) {
            double v1 = 0.0;
            double v2 = 0.0;
            if (h1.get(key) != null) {
                v1 = h1.get(key);
            }
            if (h2.get(key) != null) {
                v2 = h2.get(key);
            }

            distance += Math.abs(v1 - v2);
        }

        return distance;
    }

    /**
     * Returns a map of the names and their accumulated frequency of the given content-elements (that could be identifiers, numbers, or operators)
     *
     * @param nodes
     * @return
     */
    protected static java.util.HashMap<String, Integer> contentElementsToHistogram(org.w3c.dom.NodeList nodes) {
        final java.util.HashMap<String, Integer> histogram = new java.util.HashMap<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            String contentElementName = node.getTextContent().trim();
            // increment frequency by 1
            histogram.put(contentElementName, histogram.getOrDefault(contentElementName, 0) + 1);
        }

        return histogram;
    }

    /**
     * Adds all elements from one histogram to the other
     *
     * @param h1
     * @param h2
     * @return
     */
    protected static java.util.HashMap<String, Integer> histogramPlus(java.util.HashMap<String, Integer> h1, java.util.HashMap<String, Integer> h2) {
        final java.util.Set<String> mergedKeys = new java.util.HashSet<>(h1.keySet());
        mergedKeys.addAll(h2.keySet());
        final java.util.HashMap<String, Integer> mergedHistogram = new java.util.HashMap<>();

        for (String key : mergedKeys) {
            mergedHistogram.put(
                    key,
                    h1.getOrDefault(key, 0)
                            + h2.getOrDefault(key, 0)
            );
        }

        return mergedHistogram;
    }

    /**
     * converts strict content math ml to a histogram for the given tagname, e.g., ci
     *
     * @param strictCmml
     * @param tagName
     * @return
     */
    private static java.util.HashMap<String, Integer> strictCmmlInfoToHistogram(CMMLInfo strictCmml, String tagName) {
        final org.w3c.dom.NodeList elements = strictCmml.getElementsByTagName(tagName);
        return contentElementsToHistogram(elements);
    }

    /**
     * converts content math ml to a histogram for the given tagname, e.g., cn
     *
     * @param node
     * @param tagName
     * @return
     */
    private static java.util.HashMap<String, Integer> cmmlNodeToHistrogram(org.w3c.dom.Node node, String tagName) throws javax.xml.xpath.XPathExpressionException {
        final org.w3c.dom.NodeList elements = XMLHelper.getElementsB(node, "*//*:" + tagName);
        return contentElementsToHistogram(elements);
    }


    /**
     * this cleanup is necessary due to errors in the xslt conversion script (contentmathmml to strict cmml)
     *
     * @param tagName
     * @param histogram
     */
    private static void cleanupHistogram(String tagName, java.util.HashMap<String, Integer> histogram) {
        switch (tagName) {
            case "csymbol":
                histogram.remove("based_integer");
                break;
            case "ci":
                histogram.remove("integer");
                break;
            default:
        }
    }
}
