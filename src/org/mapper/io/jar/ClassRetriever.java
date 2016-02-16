package org.mapper.io.jar;

import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * This class is an abstraction to download the updated jar file and crawl the web.
 */
public abstract class ClassRetriever {

    /**
     * The name of the jar file in the local file system
     */
    protected final String fileName;

    /**
     * @param fileName the path to the jar file. Used to load and dump classes from/.
     */
    protected ClassRetriever(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * retrieves the most recent gamepack and dumps it
     * @return a map of the class name/ClassNode
     */
    public abstract Map<String, ClassNode> update();

    /**
     * loads the gamepack from the file system
     * @return a map of the class name/ClassNode
     */
    public abstract Map<String, ClassNode> local();

}
