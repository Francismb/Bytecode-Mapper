package org.mapper.renamer;

import org.objectweb.asm.commons.Remapper;

import java.util.Map;

/**
 * Created by Francis on 10/19/15.
 * Project Bytecode-Mapper.
 *
 * A Remapper implementation to map old names to new ones.
 */
public class RenamerMap extends Remapper {

    /**
     * The classproducers map(old name -> new name).
     */
    private final Map<String, String> classMap;

    /**
     * The field map(old classproducers ':' old field -> new name).
     */
    private final Map<String, String> fieldMap;

    /**
     * Constructs a new {@link RenamerMap}.
     * @param classMap the classproducers map.
     * @param fieldMap the field map.
     */
    public RenamerMap(final Map<String, String> classMap, final Map<String, String> fieldMap) {
        this.classMap = classMap;
        this.fieldMap = fieldMap;
    }

    @Override
    public String mapFieldName(String owner, String name, String desc) {
        final String hash = owner + ":" + name;
        return fieldMap.containsKey(hash) ? fieldMap.get(hash) : name;
    }

    @Override
    public String map(String typeName) {
        return classMap.containsKey(typeName) ? classMap.get(typeName) : typeName;
    }
}
