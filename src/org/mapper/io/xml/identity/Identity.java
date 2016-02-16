package org.mapper.io.xml.identity;

import java.io.Serializable;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 */
public class Identity implements Serializable {

    private final String name;
    private final String identity;

    public Identity(final String name, final String identity) {
        this.name = name;
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public String getIdentity() {
        return identity;
    }
}
