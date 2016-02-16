package org.mapper.knn.factory;

import org.objectweb.asm.tree.ClassNode;
import org.mapper.knn.Feature;

import java.util.HashMap;
import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * A FeatureFactory<T> is used to generate Features for given classes.
 * These features are used to be compared to instances in the knn classification process.
 *
 * In machine learning terms this simply generates metrics/features for the feature space.
 */
public abstract class FeatureFactory<T> {

    public final Map<String, Feature<T>> features = new HashMap<>();

    public abstract void init(final Map<String, ClassNode> classes);

    public Feature<T> produce(final String key) {
        return features.get(key);
    }

    public Feature<T>[] produce(final String[] keys) {
        final Feature<T>[] produce = new Feature[keys.length];
        int index = 0;
        for (final String t : keys) {
            if (features.containsKey(t)) produce[index++] = features.get(t);
        }
        return produce;
    }

    public Feature<T>[] produce() {
        return features.values().toArray(new Feature[features.size()]);
    }

}
