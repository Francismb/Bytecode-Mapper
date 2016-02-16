package org.mapper.knn.factory;

import org.objectweb.asm.tree.ClassNode;
import org.mapper.knn.Feature;

import java.util.HashMap;
import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 * A {@link FeatureFactory} contains features associated with a certain metric
 */
public abstract class FeatureFactory<T> {

    /**
     * The generated map of {@link Feature}.
     */
    public final Map<String, Feature<T>> features = new HashMap<>();

    /**
     * An abstract method used to generate the features.
     *
     * @param classes a {@link Map} of {@link String}, {@link ClassNode}.
     */
    public abstract void init(final Map<String, ClassNode> classes);

    /**
     * Will get a {@link Feature} with the key associated with it.
     *
     * @param key they key of the {@link Feature} in the {@link Map}.
     * @return the {@link Feature} that was associated with the key or
     * null if there is no {@link Feature} associated with the key.
     */
    public Feature<T> produce(final String key) {
        return features.get(key);
    }

    /**
     * Will get an array of {@link Feature}.
     *
     * @param keys they key's of the {@link Feature} in the {@link Map}.
     * @return an array of {@link Feature} that was associated with the keys.
     */
    public Feature<T>[] produce(final String[] keys) {
        final Feature<T>[] produce = new Feature[keys.length];
        for (int i = 0; i < keys.length; i++) {
            final String key = keys[i];
            if (features.containsKey(key)) {
                produce[i] = features.get(key);
            }
        }
        return produce;
    }

    /**
     * Will get an array of all the {@link Feature}
     *
     * @return an array of {@link Feature}
     */
    public Feature<T>[] produce() {
        return features.values().toArray(new Feature[features.size()]);
    }

}
