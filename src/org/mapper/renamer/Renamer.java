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
 * Created by Francis on 10/19/15.
 * Project Bytecode-Mapper.
 *
 * Renames old field and classproducers names to new ones.
 */
public class Renamer {

    /**
     * The classproducers map(new classproducers name -> old classproducers name)
     */
    private final Map<String, String> classMap = new HashMap<>();

    /**
     * The field map(new classproducers name ':' new field name -> old field name)
     */
    private final Map<String, String> fieldMap = new HashMap<>();

    /**
     * Constructs a new {@link Renamer}.
     * @param classIdentities used to generate the classproducers and field maps.
     */
    public Renamer(final List<ClassIdentity> classIdentities) {
        /*
         * Load the classproducers instances and field instances
         * into a classproducers map and field map
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
     * Renames the specified classproducers using a {@link RemappingClassAdapter}.
     * @param classNode the {@link ClassNode} to be renamed.
     * @param classWriter the {@link ClassWriter} to be used by the {@link RemappingClassAdapter}.
     */
    public void rename(final ClassNode classNode, final ClassWriter classWriter) {
        classNode.accept(new RemappingClassAdapter(classWriter, new RenamerMap(classMap, fieldMap)));
    }
}
