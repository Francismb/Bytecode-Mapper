package org.mapper.knn.producer.classtype;

import org.mapper.knn.Feature;
import org.mapper.knn.producer.FeatureProducer;
import org.mapper.utility.Node;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Francis on 7/04/16.
 * Project Bytecode-Mapper.
 */
public class InheritanceFeatureProducer extends FeatureProducer<ClassNode>{

    private Map<String, Feature<ClassNode>> features = new HashMap<>();

    @Override
    public Map<String, Feature<ClassNode>> getFeatures() {
        return features;
    }

    @Override
    public void visitClassNodeMap(final Map<String, ClassNode> classes) {
        final Map<String, Node<ClassNode>> nodes = new HashMap<>();
        for (final ClassNode cn : classes.values()) nodes.put(cn.name, new Node<>(cn));
        for (final Node<ClassNode> node : nodes.values()) {
            final String superName = node.value.superName;
            if (nodes.containsKey(superName)) {
                final Node<ClassNode> superNode = nodes.get(superName);
                superNode.successors.add(node);
                node.predecessors.add(superNode);
            }
        }
        for (final Node<ClassNode> node : nodes.values()) {
            double depth = 0;
            Node<ClassNode> current = node;
            while (current.predecessors.size() > 0 && (current = current.predecessors.iterator().next()) != null) {
                depth++;
            }
            features.put(node.value.name, new Feature<>(node.value, "INHERITANCE", depth));
        }
    }
}
