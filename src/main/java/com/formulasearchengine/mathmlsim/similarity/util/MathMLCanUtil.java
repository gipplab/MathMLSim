package com.formulasearchengine.mathmlsim.similarity.util;

import cz.muni.fi.mir.mathmlcanonicalization.ConfigException;
import cz.muni.fi.mir.mathmlcanonicalization.MathMLCanonicalizer;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Prepares the {@link MathMLCanonicalizer} based on our custom configuration.
 *
 * @author Vincent Stange
 */
public class MathMLCanUtil {

    private static final MathMLCanonicalizer canonicalizer;

    static {
        try (InputStream configIS = MathMLCanUtil.class.getClassLoader()
                .getResourceAsStream("com/formulasearchengine/mathmlsim/similarity/util/canonicalizer-config.xml")) {
            canonicalizer = new MathMLCanonicalizer(configIS);
        } catch (final IOException e) {
            throw new RuntimeException("Could not find config for canonicalizer, exiting", e);
        } catch (final ConfigException e) {
            throw new RuntimeException("Unable to configure canonicalizer, exiting", e);
        }
    }

    /**
     * Canonicalize an input MathML string.
     *
     * @param mathml MathML string
     * @return Canonicalized MathML string
     */
    public static String canonicalize(String mathml) {
        try {
            InputStream input = IOUtils.toInputStream(mathml, StandardCharsets.UTF_8.toString());
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            canonicalizer.canonicalize(input, output);
            String result = output.toString(StandardCharsets.UTF_8.toString());
            // xml header will not be used
            if (result.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n")) {
                result = result.substring("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".length());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
