package org.mapper.knn;

import org.mapper.knn.instance.Instance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

/**
 * Project ByteCodeMapper
 * Created by Francis on 10/19/15.
 *
 * This is an implementation of knn classification.
 * It is used to classify obfuscated fields/classes
 *
 * @param <T> is the data type of category/class
 */
public class FeatureSpace<T> {

    /**
     * The amount of nearest neighbors.
     */
    public int k = -1;

    /**
     * The features/data contained within the FeatureSpace<T>.
     */
    public final Feature<T>[][] features;

    /**
     * Constructs a new {@link FeatureSpace}.
     * @param features the features which make up this feature space.
     */
    public FeatureSpace(final Feature<T>[][] features) {
        this.features = features;
    }

    /**
     * Computes the distance's between the instances and features
     * and classifies the instance using knn.
     *
     * @param instance the instance to be computed.
     * @param lenientNeighbors if true then when adding features to the nearest neighbors it will
     *                 also add features of exact same value, this is good for large data sets
     *                 with common similarities between features.
     */
    public void compute(final Instance<T> instance, final boolean lenientNeighbors) {
        /*
         * Reset then calculate the distances between the instance and
         * the features of this feature space
         */
        for (int x = 0; x < features.length; x++) {
            for (int y = 0; y < features[x].length; y++) {
                features[x][y].distance = 0;
                if (instance.attributes.containsKey(features[x][y].type)) {
                    // Set the distance of the feature to a positive value of instance value minus feature value
                    features[x][y].distance = Math.abs(instance.attributes.get(features[x][y].type) - features[x][y].value);
                } else {
                    // If the feature type is a new one set the distance to the max value
                    features[x][y].distance = Integer.MAX_VALUE;
                }
            }
        }


        // If k is unset then we will set k to (features.length ^ 0.5)
        k = ((Long) Math.round(Math.pow(features[0].length, 0.5))).intValue();


        // Create nearest neighbours set
        final Set<Feature<T>> neighbors = new HashSet<>();

        // Sort all the features
        for (final Feature[] features : this.features) {
            Arrays.sort(features);
        }

        // Add the closest k neighbours of each neighbor type.
        for (final Feature<T>[] features : this.features) {
            for (int i = 0; i < k; i++) {
                // Add the feature to the neighbors
                final Feature<T> feature = features[i];
                neighbors.add(feature);
                /*
                 * Due to having a large data set with common similar
                 * data we also add all features with the same distance
                 * as the k closest neighbors
                 */
                if (lenientNeighbors) {
                    for (final Feature<T> similar : features) {
                        if (!similar.equals(feature) && similar.distance == feature.distance) {
                            neighbors.add(similar);
                        }
                    }
                }
            }
        }

        // The category chosen based off a majority vote of all k neighbors
        final T category = (T) getMajority(neighbors.toArray(new Feature[neighbors.size()]));

        // Calculate the total distance
        int distance = 0;
        for (int x = 0; x < features.length; x++) {
            for (int y = 0; y < features[x].length; y++) {
                if (features[x][y].category.equals(category)) {
                    distance += features[x][y].distance;
                    instance.features.add(features[x][y]);
                }
            }
        }

        // Set the instances category and distance fields
        instance.category = category;
        instance.distance = distance;
    }

    /**
     * Gets the most common <T> neighbor.
     *
     * @param neighbors an array of the nearest neighbor {@link Feature}
     * @return the most common <T> neighbor
     */
    public T getMajority(final Feature<T>[] neighbors) {

        // If there are only one neighbor just return it
        if (neighbors.length == 1)
            return neighbors[0].category;

        // If there are only two neighbors to a quick check
        if (neighbors.length == 2)
            return (neighbors[0].distance < neighbors[1].distance ? neighbors[0].category : neighbors[1].category);

        // Create an occurrence
        final Map<T, List<Feature<T>>> occurrence = new HashMap<>();
        for (final Feature<T> feature : neighbors) {
            if (occurrence.containsKey(feature.category)) {
                occurrence.get(feature.category).add(feature);
            } else {
                occurrence.put(feature.category, new ArrayList<Feature<T>>() {{
                    add(feature);
                }});
            }
        }

        // Get the most popular occurrence in the occurrence map.
        Map.Entry<T, List<Feature<T>>> popular = Collections.max(occurrence.entrySet(), new Comparator<Map.Entry<T, List<Feature<T>>>>() {
            @Override
            public int compare(Map.Entry<T, List<Feature<T>>> o1, Map.Entry<T, List<Feature<T>>> o2) {
                final int value = o1.getValue().size() - o2.getValue().size();
                if (value != 0) return value;
                int o1Distance = 0, o2Distance = 0;
                for (final Feature<T> feature : o1.getValue()) {
                    o1Distance += feature.distance;
                }
                for (final Feature<T> feature : o2.getValue()) {
                    o2Distance += feature.distance;
                }
                return o1Distance > o2Distance ? -1 : 1;
            }
        });
        return popular.getKey();
    }

}