package com.formulasearchengine.mathmlsim.distances.earthmover;

/**
 * Provides wrapping functionality for invoking the Earth Mover Distance.
 * Created by Felix Hamborg on 13.12.16.
 */
public class EarthMoverDistanceWrapper {
    public static Signature histogramToSignature(java.util.Map<String, Integer> histogram) {
        Signature signature = new Signature();
        Feature[] features = new Feature[histogram.size()];
        double[] weights = new double[histogram.size()];
        java.util.List<String> orderedKeys = new java.util.ArrayList<>(histogram.keySet());

        for (int i = 0; i < histogram.size(); i++) {
            features[i] = new Feature2D(i, histogram.get(orderedKeys.get(i)));
            weights[i] = 1.0;
        }

        signature.setFeatures(features);
        signature.setWeights(weights);
        signature.setNumberOfFeatures(features.length);

        return signature;
    }
}
