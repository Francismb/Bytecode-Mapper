package org.mapper.identity;

import org.mapper.knn.instance.Instance;

/**
 * Created by Francis on 24/02/16.
 * <p/>
 * Represents an {@link Identity} with a generic type.
 */
public class Identity<T> {

    /**
     * The name of this {@link Identity}
     */
    public final String name;

    /**
     * The {@link Instance} of this {@link Identity}.
     */
    public final Instance<T> instance;

    /**
     * Constructs a new {@link Identity}.
     * @param name the name of this {@link Identity}
     * @param instance the {@link Instance} of this {@link Identity}.
     */
    public Identity(final String name, final Instance<T> instance) {
        this.name = name;
        this.instance = instance;
    }
}
