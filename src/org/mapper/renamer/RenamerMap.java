package org.mapper.renamer;

import org.objectweb.asm.commons.Remapper;

import java.util.Map;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 *
 * A Remapper implementation to map old names to new ones.
 */
public class RenamerMap extends Remapper {

    /**
     * The class map(old name -> new name).
     */
    private final Map<String, String> classMap;

    /**
     * The field map(old class ':' old field -> new name).
     */
    private final Map<String, String> fieldMap;

    /**
     * Constructs a new {@link RenamerMap}.
     * @param classMap the class map.
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
