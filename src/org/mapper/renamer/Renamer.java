package org.mapper.renamer;

import org.mapper.identity.ClassIdentity;
import org.mapper.identity.FieldIdentity;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 *
 * Renames old field and class names to new ones.
 */
public class Renamer {

    /**
     * The class map(new class name -> old class name)
     */
    private final Map<String, String> classMap = new HashMap<>();

    /**
     * The field map(new class name ':' new field name -> old field name)
     */
    private final Map<String, String> fieldMap = new HashMap<>();

    /**
     * Constructs a new {@link Renamer}.
     * @param classIdentities used to generate the class and field maps.
     */
    public Renamer(final List<ClassIdentity> classIdentities) {
        /*
         * Load the class instances and field instances
         * into a class map and field map
         */
        for (final ClassIdentity classInstance : classIdentities) {
            classMap.put(classInstance.instance.category.name, classInstance.name);
            for (final FieldIdentity fieldInstance : classInstance.fields) {
                final String hash = classInstance.instance.category.name + ":" + fieldInstance.instance.category.name;
                fieldMap.put(hash, fieldInstance.name);
            }
        }
    }

    /**
     * Renames the specified class using a {@link RemappingClassAdapter}.
     * @param classNode the {@link ClassNode} to be renamed.
     * @param classWriter the {@link ClassWriter} to be used by the {@link RemappingClassAdapter}.
     */
    public void rename(final ClassNode classNode, final ClassWriter classWriter) {
        classNode.accept(new RemappingClassAdapter(classWriter, new RenamerMap(classMap, fieldMap)));
    }
}
