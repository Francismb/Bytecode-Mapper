package org.mapper.io.xml.identity;

import java.io.Serializable;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 */
public class ClassIdentity extends Identity implements Serializable {

    public ClassIdentity(final String name, final String identity) {
        super(name, identity);
    }

}
