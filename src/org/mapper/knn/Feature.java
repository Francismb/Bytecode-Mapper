package org.mapper.knn;

import org.mapper.knn.instance.Instance;

/**
 * Project ByteCodeMapper
 * Created by Francis on 10/19/15.
 *
 * A {@link Feature} represents a category(class or field) and a type and value.
 * A {@link Feature} is compared to a {@link Instance} to calculate distance between them.
 */
public class Feature<T> implements Comparable<Feature<T>> {

    /**
     * The category that this {@link Feature} represents
     */
    public T category;

    /**
     * The distance to the {@link Instance}
     */
    public double distance = 0;

    /**
     * The type of this {@link Feature}
     */
    public final String type;

    /**
     * The value of this {@link Feature}
     */
    public final Double value;

    /**
     * Constructs a new {@link Feature}
     * @param category the category associated with this {@link Feature}
     * @param type the type of this {@link Feature}
     * @param value the value of this {@link Feature}
     */
    public Feature(final T category, final String type, final Double value) {
        this.category = category;
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        return category != null &&
                o instanceof Feature &&
                ((Feature) o).category.equals(category) &&
                ((Feature) o).type.equals(type) &&
                ((Feature) o).value.equals(value);
    }

    @Override
    public int compareTo(final Feature<T> feature) {
        return Double.compare(distance, feature.distance);
    }
}