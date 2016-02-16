package org.mapper;

import org.mapper.knn.Feature;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/**
 * Project ByteCodeMapper
 * Created by Francis on 30/11/2015.
 */
public interface PatternCreator {

    public Feature<ClassNode>[] create(final String className);

    public Feature<FieldNode>[] create(final String className, final String fieldName);

}
