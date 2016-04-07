package org.mapper.knn.producer;

import org.mapper.knn.Feature;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.Map;

/**
 * Created by Francis on 8/03/16.
 * Project Bytecode-Mapper.
 */
public abstract class FeatureProducer<T> {

    /**
     * A getter method to obtain the {@link Map} of <{@link String}, {@link Feature}>
     *
     * @return A {@link Map} of <{@link String}, {@link Feature}>
     */
    public abstract Map<String, Feature<T>> getFeatures();

    /**
     * Visits a {@link ClassNode}
     * @param classNode the {@link ClassNode} to be visited
     */
    public void visitClassNode(final ClassNode classNode) {}

    /**
     * Visits a {@link FieldNode}
     * @param classnode the {@link FieldNode} parent
     * @param fieldNode the {@link FieldNode} to visit
     */
    public void visitFieldNode(final ClassNode classnode, final FieldNode fieldNode){}

    /**
     * Visits a {@link Map} of classes
     * @param classes the {@link Map} of classes to visit
     */
    public void visitClassNodeMap(final Map<String, ClassNode> classes) {}

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

    /**
     * Processes all the {@link FeatureProducer}
     * @param producers the array of producers to process
     * @param classes the classes to use to process the producers
     */
    public static void process(final FeatureProducer[] producers, final Map<String, ClassNode> classes) {
        for (final FeatureProducer producer : producers) {
            for (final ClassNode classNode : classes.values()) {
                // visit the ClassNode
                producer.visitClassNode(classNode);

                for (final FieldNode fieldNode : classNode.fields) {
                    // Visit the FieldNode
                    producer.visitFieldNode(classNode, fieldNode);
                }
            }
            // Visit the whole map of classes
            producer.visitClassNodeMap(classes);
        }
    }
}
