package org.mapper.io.xml.identity;

import java.io.Serializable;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 */
public class FieldIdentity extends Identity implements Serializable {

    private final String class_name;
    private final int multiplier;

    public FieldIdentity(final String name, final String identity, final String class_name, final int multiplier) {
        super(name, identity);
        this.multiplier = multiplier;
        this.class_name = class_name;
    }

    public String getClassName() {
        return class_name;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
