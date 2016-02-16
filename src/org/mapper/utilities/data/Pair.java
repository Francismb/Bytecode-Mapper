package org.mapper.utilities.data;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * A simple key <-> value pair object
 */
public class Pair<A, B> {

    public A key;
    public B value;

    public Pair(final A key, final B value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            final Pair<A, B> pair = (Pair<A, B>) other;
            return pair.key.equals(key) && pair.value.equals(value);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }

}