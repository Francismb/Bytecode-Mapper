package org.mapper.knn;

import org.mapper.utilities.Pair;

import java.io.Serializable;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 *
 * A Feature<T> represents a category(class or field) and a attribute<key, value>.
 * A Feature<T> is compared to a Instance<T> to calculate distance in the FeatureSpace.
 */
public class Feature<T> implements Comparable<Feature<T>>, Serializable {

    /**
     * The class/category instance that this Feature<T> represents
     */
    public T category;

    /**
     * The distance to the Feature<T> instance
     */
    public int distance = 0;

    /**
     * The attributes of this Feature<T>
     */
    public final Pair<String, Double> attribute;

    public Feature(final T category, final Pair<String, Double> attribute) {
        this.category = category;
        this.attribute = attribute;
    }

    @Override
    public int compareTo(final Feature<T> feature) {
        return distance - feature.distance;
    }

    @Override
    public boolean equals(Object o) {
        return category != null && o instanceof Feature && ((Feature) o).category.equals(category) && ((Feature) o).attribute.equals(attribute);
    }
}