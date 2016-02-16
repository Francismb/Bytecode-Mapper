package org.mapper.refactor;

import org.objectweb.asm.commons.Remapper;

import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * A Remapper implementation.
 */
public class RefactorizerMapper extends Remapper {

    private final Map<String, String> classMap;
    private final Map<String, String> fieldMap;

    public RefactorizerMapper(final Map<String, String> classMap, final Map<String, String> fieldMap) {
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
