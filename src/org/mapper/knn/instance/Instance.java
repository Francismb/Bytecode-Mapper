package org.mapper.knn.instance;

import org.mapper.knn.Feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 *
 * A Instance<T> represents a list of Features which are used
 * to categorise the instance.
 */
public class Instance<T> implements Serializable {

    /**
     * The class/category which has been assigned to this instance after analysis
     */
    public T category = null;

    /**
     * The sum of the features with the same @category
     */
    public int distance = 0;

    /**
     * A list of features which have the category of this instance
     */
    public final List<Feature<T>> features = new ArrayList<>();

    /**
     * The attributes of this Feature<T>
     * Values are assorted in a Key => Value like structure
     */
    public final Map<String, Double> attributes = new HashMap<>();

}
