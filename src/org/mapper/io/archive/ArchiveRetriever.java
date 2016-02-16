package org.mapper.io.archive;

import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 * This class is an abstraction to download a jar file or
 * load a jar file from the local file system.
 */
public interface ArchiveRetriever {

    /**
     * Retrieves a jar file and writes it to the local storage.
     * @return a {@link Map} of the class Name, {@link ClassNode}
     */
    public Map<String, ClassNode> update();

    /**
     * loads the jar file from the local storage
     * @return a {@link Map} of the class Name, {@link ClassNode}
     */
    public Map<String, ClassNode> local();

}
