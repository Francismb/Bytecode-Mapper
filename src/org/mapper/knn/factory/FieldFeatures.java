package org.mapper.knn.factory;

import static org.objectweb.asm.Opcodes.*;

import org.mapper.utilities.Pair;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.SourceValue;
import org.mapper.knn.Feature;

import java.util.List;
import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 * A class containing {@link FeatureFactory} used to generate features for fields.
 */
public class FieldFeatures {

    public static String hash(final ClassNode classNode, final FieldNode fieldNode) {
        return classNode.name + ":" + fieldNode.name;
    }

    public static String hash(final FieldInsnNode fieldInstruction) {
        return fieldInstruction.owner + ":" + fieldInstruction.name;
    }

    public static String[] hash(final ClassNode cn, final List<FieldNode> fields) {
        final String[] hashs = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            hashs[i] = hash(cn, fields.get(i));
        }
        return hashs;
    }

    public static final FeatureFactory<FieldNode> TYPES = new FeatureFactory<FieldNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final ClassNode cn : classes.values()) {
                for (final FieldNode fn : cn.fields) {
                    double value = Double.MAX_VALUE;
                    switch (fn.desc) {
                        case "B":
                            value = 0;
                            break;
                        case "S":
                            value = 1;
                            break;
                        case "I":
                            value = 2;
                            break;
                        case "F":
                            value = 3;
                            break;
                        case "J":
                            value = 4;
                            break;
                        case "D":
                            value = 5;
                            break;
                        case "C":
                            value = 15;
                            break;
                        case "Z":
                            value = 100;
                            break;
                        default:
                            if (fn.desc.contains("[") && !fn.desc.contains("L")) {
                                value = 200;
                            } else if (fn.desc.contains("L")) {
                                value = 300;
                            }
                            break;
                    }
                    final Feature<FieldNode> feature = new Feature<>(fn, new Pair<>("TYPES", value));
                    features.put(hash(cn, fn), feature);
                }
            }
        }
    };

    public static final FeatureFactory<FieldNode> REFERENCES = new FeatureFactory<FieldNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final ClassNode cn : classes.values()) {
                for (final FieldNode fn : cn.fields) {
                    final String hash = hash(cn, fn);
                    final Feature<FieldNode> feature = new Feature<FieldNode>(
                            fn,
                            new Pair<>("REFERENCES", 0.0)
                    );
                    features.put(hash, feature);
                }
            }
            for (final ClassNode cn : classes.values()) {
                for (final MethodNode mn : cn.methods) {
                    mn.accept(new MethodVisitor() {
                        @Override
                        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                            final String hash = owner + ":" + name;
                            if (features.containsKey(hash)) {
                                features.get(hash).attribute.value++;
                            }
                        }
                    });
                }
            }
        }
    };

    public static final FeatureFactory<FieldNode> MULTIPLICATIONS = new FeatureFactory<FieldNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final ClassNode cn : classes.values()) {
                for (final FieldNode fn : cn.fields) {
                    final String hash = hash(cn, fn);
                    final Feature<FieldNode> feature = new Feature<FieldNode>(
                            fn,
                            new Pair<>("MULTIPLICATIONS", 0.0)
                    );
                    features.put(hash, feature);
                }
            }
            for (final ClassNode cn : classes.values()) {
                for (final MethodNode mn : cn.methods) {
                    for (int i = 0; i < mn.instructions.size(); i++) {
                        final AbstractInsnNode insn = mn.instructions.get(i);
                        final int opcode = insn.getOpcode();
                        if (opcode == IMUL || opcode == DMUL || opcode == LMUL || opcode == FMUL) {
                            final Frame<SourceValue> frame = Analyzer.getFromCache(cn, mn)[i];
                            if (frame.getStackSize() == 2) {
                                final FieldInsnNode field = frame.getInsn(FieldInsnNode.class, 0, 1);
                                if (field != null) {
                                    final String hash = hash(field);
                                    if (features.containsKey(hash)) {
                                        features.get(hash).attribute.value++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    public static final FeatureFactory<FieldNode> ADDITIONS = new FeatureFactory<FieldNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final ClassNode cn : classes.values()) {
                for (final FieldNode fn : cn.fields) {
                    final String hash = hash(cn, fn);
                    final Feature<FieldNode> feature = new Feature<FieldNode>(
                            fn,
                            new Pair<>("ADDITIONS", 0.0)
                    );
                    features.put(hash, feature);
                }
            }
            for (final ClassNode cn : classes.values()) {
                for (final MethodNode mn : cn.methods) {
                    for (int i = 0; i < mn.instructions.size(); i++) {
                        final AbstractInsnNode insn = mn.instructions.get(i);
                        final int opcode = insn.getOpcode();
                        if (opcode == IADD || opcode == DADD || opcode == LADD || opcode == FADD) {
                            final Frame<SourceValue> frame = Analyzer.getFromCache(cn, mn)[i];
                            if (frame.getStackSize() == 2) {
                                final FieldInsnNode field = frame.getInsn(FieldInsnNode.class, 0, 1);
                                if (field != null) {
                                    final String hash = hash(field);
                                    if (features.containsKey(hash)) {
                                        features.get(hash).attribute.value++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    public static final FeatureFactory<FieldNode> SUBTRACTIONS = new FeatureFactory<FieldNode>() {
        @Override
        public void init(final Map<String, ClassNode> classes) {
            for (final ClassNode cn : classes.values()) {
                for (final FieldNode fn : cn.fields) {
                    final String hash = hash(cn, fn);
                    final Feature<FieldNode> feature = new Feature<FieldNode>(
                            fn,
                            new Pair<>("SUBTRACTIONS", 0.0)
                    );
                    features.put(hash, feature);
                }
            }
            for (final ClassNode cn : classes.values()) {
                for (final MethodNode mn : cn.methods) {
                    for (int i = 0; i < mn.instructions.size(); i++) {
                        final AbstractInsnNode insn = mn.instructions.get(i);
                        final int opcode = insn.getOpcode();
                        if (opcode == ISUB || opcode == DSUB || opcode == LSUB || opcode == FSUB) {
                            final Frame<SourceValue> frame = Analyzer.getFromCache(cn, mn)[i];
                            if (frame.getStackSize() == 2) {
                                final FieldInsnNode field = frame.getInsn(FieldInsnNode.class, 0, 1);
                                if (field != null) {
                                    final String hash = hash(field);
                                    if (features.containsKey(hash)) {
                                        features.get(hash).attribute.value++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    };
}
