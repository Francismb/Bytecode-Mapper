package org.mapper.utilities;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 */
public class HashNumber extends Number {

    private final Object value;

    public HashNumber(final Object value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return (double) value;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof HashNumber)) return false;
        return o.hashCode() == hashCode();
    }
}
