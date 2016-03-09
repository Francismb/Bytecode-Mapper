package org.mapper.knn.producer;

import org.mapper.knn.Feature;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Francis on 8/03/16.
 */
public abstract class FeatureProducer<T> {

    /**
     * A getter method to obtain the {@link Map} of <{@link String}, {@link Feature}>
     *
     * @return A {@link Map} of <{@link String}, {@link Feature}>
     */
    public abstract Map<String, Feature<T>> getFeatures();

    public void visitClassNode(final ClassNode classNode) {
    }
    public void visitPack(final Map<String, ClassNode> classes) {
    }

    /**
     * Will get an array of all the {@link Feature} from {@link #getFeatures()}
     *
     * @return an array of {@link Feature} from {@link #getFeatures()}
     */
    public Feature<T>[] produce() {
        final Map<String, Feature<T>> features = getFeatures();
        if (features == null) {
            throw new NullPointerException("Features have not been created yet");
        }
        return features.values().toArray(new Feature[features.size()]);
    }

    /**
     * Will get a {@link Feature} from {@link #getFeatures()} with the
     * key associated with it.
     *
     * @param key the key of the {@link Feature} from {@link #getFeatures()}
     * @return the {@link Feature} that was associated with the key or
     * null if there is no {@link Feature} associated with the key.
     */
    public Feature<T> produce(final String key) {
        final Map<String, Feature<T>> features = getFeatures();
        if (features == null) {
            throw new NullPointerException("Features have not been created yet");
        }
        return features.get(key);
    }
}
