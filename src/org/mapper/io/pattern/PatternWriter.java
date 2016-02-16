package org.mapper.io.pattern;

import org.mapper.knn.instance.ClassInstance;

import java.util.List;

/**
 * Project ByteCodeMapper
 * Created by Francis on 29/11/2015.
 */
public interface PatternWriter {

    /**
     * Writes the class instances to a file
     *
     * @param path      the path of the file to write
     * @param instances the computed class instances
     */
    public void write(final String path, final List<ClassInstance> instances);

}
