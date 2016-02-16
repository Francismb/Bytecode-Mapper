package org.mapper.requisite;

import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * A resource is a resource which has been found within the game.
 * Examples being multipliers or game versions.
 */
public abstract class Resource<T> {

    /**
     * value is a cache of the resource to be used
     */
    public T value;

    /**
     * Searches the classes for the resource
     * @param classes is a map of classes which are searched through
     */
    public abstract void search(final Map<String, ClassNode> classes);

}
