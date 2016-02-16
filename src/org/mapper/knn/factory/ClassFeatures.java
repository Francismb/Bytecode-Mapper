package org.mapper.knn.factory;

import static org.objectweb.asm.Opcodes.*;

import org.mapper.knn.Feature;
import org.mapper.utilities.Type;
import org.mapper.utilities.Pair;
import org.mapper.utilities.Node;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 * A class containing {@link FeatureFactory} used to generate features for classes.
 */
public class ClassFeatures {

    public static final FeatureFactory<ClassNode> ARRAY1 = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("ARRAY1", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, ACC_STATIC) && (fn.desc.length() - fn.desc.replace("[", "").length()) == 1) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> ARRAY2 = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("ARRAY2", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && (fn.desc.length() - fn.desc.replace("[", "").length()) == 2) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> ARRAY3 = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("ARRAY3", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && (fn.desc.length() - fn.desc.replace("[", "").length()) == 3) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> BOOLEAN = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("BOOLEANS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("Z")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> BYTE = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("BYTES", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("B")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> CHAR = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("CHARS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("C")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> DOUBLE = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("DOUBLES", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("D")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> FLOAT = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("FLOATS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("F")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> INTEGER = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("INTEGERS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("I")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> LONG = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("LONGS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("J")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> SHORT = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("SHORTS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.equals("S")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> OBJECT = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("OBJECTS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC) && fn.desc.startsWith("L") && fn.desc.endsWith(";")) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> FIELDS = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("FIELDS", 0.0));
                for (final FieldNode fn : entry.getValue().fields) {
                    if (Type.isNot(fn.access, Opcodes.ACC_STATIC)) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> METHODS = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("METHODS", 0.0));
                for (final MethodNode mn : entry.getValue().methods) {
                    if (Type.isNot(mn.access, Opcodes.ACC_STATIC)) {
                        feature.attribute.value++;
                    }
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> INHERITANCE = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            final Map<String, Node<ClassNode>> nodes = new HashMap<>();
            for (final ClassNode cn : classes.values()) nodes.put(cn.name, new Node<>(cn));
            for (final Node<ClassNode> node : nodes.values()) {
                final String superName = node.t.superName;
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
                features.put(node.t.name, new Feature<>(node.t, new Pair<>("INHERITANCE", depth)));
            }
        }
    };

    public static final FeatureFactory<ClassNode> INTERFACE = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("INTERFACES", (double) entry.getValue().interfaces.size()));
                features.put(entry.getKey(), feature);
            }
        }
    };

    public static final FeatureFactory<ClassNode> SUPER = new FeatureFactory<ClassNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                final Feature<ClassNode> feature = new Feature<>(entry.getValue(), new Pair<>("SUPER", 0.0));
                if (!entry.getValue().superName.equals("java/lang/Object")) {
                    feature.attribute.value++;
                }
                features.put(entry.getKey(), feature);
            }
        }
    };

}
