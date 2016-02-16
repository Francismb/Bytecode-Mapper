package org.mapper.io.pattern;

import org.mapper.knn.instance.ClassInstance;

import java.util.List;

/**
 * Project ByteCodeMapper
 * Created by Francis on 29/11/2015.
 */
public interface PatternReader {

    /**
     * Reads a file for class instances
     *
     * @param path the path of the file to read
     * @return a list of {@link ClassInstance}
     */
    public List<ClassInstance> read(final String path);

}
