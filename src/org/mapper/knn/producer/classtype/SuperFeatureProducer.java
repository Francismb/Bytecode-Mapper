package org.mapper.knn.producer.classtype;

import org.mapper.knn.Feature;
import org.mapper.knn.producer.FeatureProducer;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Francis on 7/04/16.
 * Project Bytecode-Mapper.
 */
public class SuperFeatureProducer extends FeatureProducer<ClassNode>{

    private Map<String, Feature<ClassNode>> features = new HashMap<>();

    @Override
    public Map<String, Feature<ClassNode>> getFeatures() {
        return features;
    }

    @Override
    public void visitClassNode(final ClassNode classNode) {
        final Feature<ClassNode> feature = new Feature<>(classNode, "INTERFACES", 0.0);
        if (!classNode.superName.equals("java/lang/Object")) {
            feature.value++;
        }
        features.put(classNode.name, feature);
    }
}
