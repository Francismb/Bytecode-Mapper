package org.mapper.utilities.bytecode;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 */
public class Type {

    public static boolean is(final int access, final int opcode) {
        return (access & opcode) != 0;
    }

    public static boolean isNot(final int access, final int opcode) {
        return (access & opcode) == 0;
    }

}
