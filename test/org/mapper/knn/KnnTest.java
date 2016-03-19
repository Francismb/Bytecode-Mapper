package org.mapper.knn;

import org.mapper.knn.instance.Instance;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by francis on 22/02/16.
 */
public class KnnTest {

    private static final String classOne = "Class One";
    private static final String classTwo = "Class Two";

    public static void main(final String[] args) {
        // Create some features with test data
        final Feature<Integer>[][] features = new Feature[3][];
        features[0] = new Feature[]{
                new Feature(classOne, "attribute_one", 5.2),
                new Feature(classTwo, "attribute_one", 4.2)
        };
        features[1] = new Feature[]{
                new Feature(classOne, "attribute_two", 99.2),
                new Feature(classTwo, "attribute_two", 75.2)
        };
        features[2] = new Feature[]{
                new Feature(classOne, "attribute_three", 938.2),
                new Feature(classTwo, "attribute_three", 1023.3)
        };

        // Create a map with the instance attributes
        final Map<String, Double> attributes = new HashMap<>();
        attributes.put("attribute_one", 4.5);
        attributes.put("attribute_two", 82.2);
        attributes.put("attribute_three", 100002.2);

        // Create an instance with the attributes
        final Instance instance = new Instance(attributes);

        // Create a feature space with the features
        final FeatureSpace space = new FeatureSpace(features);
        space.k = 1;

        // Compute the instance
        space.compute(instance, false);

        // Output the instances classproducers
        System.out.println("Knn computed the instance to be of classproducers" + instance.category);
    }

}
