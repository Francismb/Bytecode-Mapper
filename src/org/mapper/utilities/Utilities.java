package org.mapper.utilities;

import org.objectweb.asm.ClassWriter;
import org.mapper.knn.instance.ClassInstance;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Printer;
import org.mapper.knn.Feature;
import org.mapper.knn.instance.FieldInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 */
public class Utilities {

    public static void dump(final String location, final Map<String, ClassNode> classes) {
        try {
            final FileOutputStream stream = new FileOutputStream(new File(location));
            final JarOutputStream out = new JarOutputStream(stream);
            for (final ClassNode cn : classes.values()) {
                final JarEntry entry = new JarEntry(cn.name + ".class");
                final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                out.putNextEntry(entry);
                cn.accept(writer);
                out.write(writer.toByteArray());
            }
            out.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getMostOccuring(final Map<T, ?> data, final Comparator comparator ) {
        Map.Entry<T, ?> bestOccurrence = null;
        for (final Map.Entry<T, ?> occurence : data.entrySet()) {
            if (bestOccurrence == null || comparator.compare(bestOccurrence.getValue(), occurence.getValue()) > 0) {
                bestOccurrence = occurence;
            }
        }
        return bestOccurrence != null ? bestOccurrence.getKey() : null;
    }

    public static boolean inRange(final int a, final int b, final int val) {
        final int high = a > b ? a : b;
        final int low = a > b ? b : a;
        return val >= low && val <= high;
    }

    public static String opcodeToString(final int opcode) {
        if (opcode == -1) return "LABEL";
        return Printer.OPCODES[opcode];
    }

    public static AbstractInsnNode getNext(AbstractInsnNode insn) {
        while (insn != null && insn.getOpcode() == -1) insn = insn.getNext();
        return insn;
    }

    public static FieldNode getField(final Map<String, ClassNode> classes, final String fieldName, final String fieldDesc, final String className) {
        final ClassNode cn = classes.get(className);
        if (cn != null) {
            for (final FieldNode fn : cn.fields) {
                if (fn.name.equals(fieldName) && fn.desc.equals(fieldDesc)) {
                    return fn;
                }
            }
        }
        return null;
    }

    public static MethodNode getMethod(final Map<String, ClassNode> classes, final String methodName, final String methodDesc, final String className) {
        final ClassNode cn = classes.get(className);
        if (cn != null) {
            for (final MethodNode mn : cn.methods) {
                if (mn.name.equals(methodName) && mn.desc.equals(methodDesc)) {
                    return mn;
                }
            }
        }
        return null;
    }

    public static String createOutput(final ClassInstance instance) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n-> " + instance.name + " <===> " + instance.category.name + " || [DISTANCE(" + instance.distance + "), INSTANCE(");
        boolean first = true;
        for (final Map.Entry<String, Double> e : instance.attributes.entrySet()) {
            if (!first) {
                builder.append(" ");
            }
            builder.append(e.getValue());
            first = false;
        }
        builder.append("), FEATURES(");
        first = true;
        for (final Map.Entry<String, Double> e : instance.attributes.entrySet()) {
            for (final Feature<ClassNode> feature : instance.features) {
                if (feature.attribute.key.equals(e.getKey())) {
                    if (!first) {
                        builder.append(" ");
                    }
                    builder.append(feature.attribute.value);
                    first = false;
                    break;
                }
            }
        }
        builder.append(")]");
        return builder.toString();
    }

    public static String createOutput(final FieldInstance instance) {
        final StringBuilder builder = new StringBuilder();
        builder.append("  -> " + instance.name + " <===> " + instance.category.name + " || [DISTANCE(" + instance.distance + "), INSTANCE(");
        boolean first = true;
        for (final Map.Entry<String, Double> e : instance.attributes.entrySet()) {
            if (!first) {
                builder.append(" ");
            }
            builder.append(e.getValue());
            first = false;
        }
        builder.append("), FEATURES(");
        first = true;
        for (final Map.Entry<String, Double> e : instance.attributes.entrySet()) {
            for (final Feature<FieldNode> feature : instance.features) {
                if (feature.attribute.key.equals(e.getKey())) {
                    if (!first) {
                        builder.append(" ");
                    }
                    builder.append(feature.attribute.value);
                    first = false;
                    break;
                }
            }
        }
        builder.append(")]");
        return builder.toString();
    }
}
