package org.mapper.refactor;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.tree.ClassNode;
import org.mapper.knn.instance.ClassInstance;
import org.mapper.knn.instance.FieldInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * Refactors a jar file and dumps it to the disk.
 */
public class Refactorizer {

    private final String file;
    private final List<ClassInstance> instances;
    private final Map<String, ClassNode> classes;

    public Refactorizer(final String file, final Map<String, ClassNode> classes, final List<ClassInstance> instances) {
        if (file.contains("/")) {
            this.file = file;
        } else {
            this.file = "./" + file;
        }
        this.classes = classes;
        this.instances = instances;
    }

    public void refactor() {
        final Map<String, String> classMap = new HashMap<>();
        final Map<String, String> fieldMap = new HashMap<>();

        /**
         * Load the class instances and field instances
         * into a key => value map.
         */
        for (final ClassInstance classInstance : instances) {
            classMap.put(classInstance.category.name, classInstance.name);
            for (final FieldInstance fieldInstance : classInstance.fields) {
                final String hash = classInstance.category.name + ":" + fieldInstance.category.name;
                fieldMap.put(hash, fieldInstance.name);
            }
        }

        /**
         * Refactor the class files using a class visitor and
         * dump the classes.
         */
        try {
            final FileOutputStream stream = new FileOutputStream(new File(file));
            final JarOutputStream out = new JarOutputStream(stream);
            for (final ClassNode cn : classes.values()) {
                /**
                 * Refactor the class files name
                 */
                String className = cn.name;
                if (classMap.containsKey(className)) {
                    className = classMap.get(className);
                }
                final JarEntry entry = new JarEntry(className + ".class");

                final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                out.putNextEntry(entry);
                /**
                 * Refactoring classes here
                 */
                cn.accept(new RemappingClassAdapter(writer, new RefactorizerMapper(classMap, fieldMap)));

                /**
                 * Write the bytes of the class
                 */
                out.write(writer.toByteArray());
            }
            out.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
