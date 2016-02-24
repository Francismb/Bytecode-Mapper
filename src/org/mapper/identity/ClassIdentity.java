package org.mapper.identity;

import org.mapper.knn.instance.Instance;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Francis on 24/02/16.
 *
 * Represents a java class identity
 */
public class ClassIdentity extends Identity<ClassNode> {

    /**
     * The mapped name of this {@link Identity}.
     */
    public final String name;

    /**
     * A set of child {@link FieldIdentity}.
     */
    public final Set<FieldIdentity> fields = new HashSet<>();

    /**
     * Constructs a new {@link FieldIdentity}.
     *
     * @param name     the mapped name.
     * @param instance the {@link Instance} of this {@link FieldIdentity}.
     */
    public ClassIdentity(final String name, final Instance<ClassNode> instance) {
        super(instance);
        this.name = name;
    }

    /**
     * Adds a {@link FieldIdentity} to the fields set.
     * @param identity the {@link FieldIdentity} to add.
     */
    public void addFieldIdentity(final FieldIdentity identity) {
        fields.add(identity);
    }
}
