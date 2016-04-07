package org.mapper.utility;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Francis on 7/04/16.
 * Project Bytecode-Mapper.
 */
public class Node<T> {

    /**
     * value represents the object which this node represents
     */
    public final T value;

    /**
     * The successors of this node
     */
    public final Set<Node<T>> successors = new HashSet<>();
    public final Set<Node<T>> predecessors = new HashSet<>();

    public Node(final T value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Node) {
            final Node node = (Node) object;
            return node.value.equals(value);
        }
        return false;
    }
}