package org.mapper.utilities;

import java.util.HashSet;
import java.util.Set;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 */
public class Node<T> {

    /**
     * t represents the object which this node represents
     */
    public final T t;

    /**
     * The successors of this node
     */
    public final Set<Node<T>> successors = new HashSet<>();
    public final Set<Node<T>> predecessors = new HashSet<>();

    public Node(final T t) {
        this.t = t;
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Node) {
            final Node node = (Node) object;
            return node.t.equals(t);
        }
        return false;
    }
}
