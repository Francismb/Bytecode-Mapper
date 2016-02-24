package org.mapper.identity;

import org.mapper.knn.instance.Instance;

/**
 * Created by Francis on 24/02/16.
 * <p/>
 * Represents an {@link Identity} with a generic type.
 */
public class Identity<T> {

    /**
     * The {@link Instance} of this {@link Identity}.
     */
    public final Instance<T> instance;

    /**
     * Constructs a new {@link Identity}.
     *
     * @param instance the {@link Instance} of this {@link Identity}.
     */
    public Identity(final Instance<T> instance) {
        this.instance = instance;
    }
}
