package org.mapper.requisite;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * This is the abstract class which is used to transform the game
 */
public abstract class Transformer implements Opcodes {

    public abstract void transform(final Map<String, ClassNode> classes);
}