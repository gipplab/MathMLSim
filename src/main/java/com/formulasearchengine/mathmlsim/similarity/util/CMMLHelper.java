package com.formulasearchengine.mathmlsim.similarity.util;

import com.formulasearchengine.mathmltools.mml.CMMLInfo;
import com.formulasearchengine.mathmltools.xmlhelper.XMLHelper;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

/**
 * The class contains static methods for direct access
 * to the content semantics of a MathML string or CMMLInfo document.
 *
 * @author Vincent Stange
 */
public class CMMLHelper {

    /**
     * Get the first node of the MathML-Content annotations within a MathML document.
     *
     * @param mathml full MathML document
     * @return first node of the MathML-Content annotations within a MathML document
     */
    public static Node getCmml(String mathml) {
        try {
            // get the apply node of the ContentMathML root
            return getFirstCmmlNode(new CMMLInfo(mathml));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the first node of the MathML-Content annotations within a MathML document.
     * Before, the MathML document was converted to strict CMML and subsequently also converted
     * to display only the abstract variation of the content dictionary.
     *
     * @param mathml full MathML document
     * @return first node of the MathML-Content annotations within a MathML document
     */
    public static Node getAbstractCmml(String mathml) {
        try {
            // get ContentMathML to Strict ContentMathML and finally the abstract CD
            CMMLInfo cmmlInfo = new CMMLInfo(mathml).toStrictCmml().abstract2CDs();
            // get the apply node of the ContentMathML root
            return getFirstCmmlNode(cmmlInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the first node of the MathML-Content annotations within a CMMLInfo document.
     *
     * @param cmmlInfo CMMLInfo document
     * @return first node of the MathML-Content annotations within a MathML document
     */
    public static Node getFirstCmmlNode(CMMLInfo cmmlInfo) {
        try {
            XPath xpath = XMLHelper.namespaceAwareXpath("m", CMMLInfo.NS_MATHML);
            return (Node) xpath.compile("*//m:annotation-xml[@encoding='MathML-Content']/m:apply").evaluate(cmmlInfo, XPathConstants.NODE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
