package org.mapper;

import org.mapper.knn.instance.ClassInstance;

/**
 * Project ByteCodeMapper
 * Created by Francis on 30/11/2015.
 */
public interface PatternMapper {

    public void map(final Iterable<ClassInstance> instances);

}
