package org.mapper.utility;

/**
 * Created by Francis on 10/19/15.
 * Project Bytecode-Mapper.
 */
public class Type {

    public static boolean is(final int access, final int opcode) {
        return (access & opcode) != 0;
    }

    public static boolean isNot(final int access, final int opcode) {
        return (access & opcode) == 0;
    }

}
