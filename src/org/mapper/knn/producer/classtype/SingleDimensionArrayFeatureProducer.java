package org.mapper.knn.producer.classtype;

import org.mapper.knn.Feature;
import org.mapper.knn.producer.FeatureProducer;
import org.mapper.utility.Type;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Francis on 9/03/16.
 */
public class SingleDimensionArrayFeatureProducer extends FeatureProducer<ClassNode> {

    private Map<String, Feature<ClassNode>> features = new HashMap<>();

    @Override
    public Map<String, Feature<ClassNode>> getFeatures() {
        return features;
    }

    @Override
    public void visitClassNode(final ClassNode classNode) {
        final Feature<ClassNode> feature = new Feature<>(classNode, "ARRAY1", 0.0);
        for (final FieldNode fn : classNode.fields) {
            if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && (fn.desc.length() - fn.desc.replace("[", "").length()) == 1) {
                feature.value++;
            }
        }
        features.put(classNode.name, feature);
    }
}
