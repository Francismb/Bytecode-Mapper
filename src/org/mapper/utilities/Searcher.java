package org.mapper.utilities;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 *
 * Searches for bytecode patterns with regular expressions
 */
public class Searcher {

    /**
     * Searches through a list of instruction trying to find a regular expression match
     *
     * @param method the method to search through
     * @param regex the regular expression which is applied to the instruction list
     * @return a list of instruction matches
     */
    public static List<AbstractInsnNode[]> search(final MethodNode method, String regex) {
        return search(method.instructions, regex);
    }

    /**
     * Searches through a list of instruction trying to find a regular expression match
     *
     * @param instrctions the instructions to search through
     * @param regex the regular expression which is applied to the instruction list
     * @return a list of instruction matches
     */
    public static List<AbstractInsnNode[]> search(final InsnList instrctions, String regex) {
        if (!regex.endsWith(" ")) {
            regex += " ";
        }
        final Pattern pattern = Pattern.compile(regex);

        final String compiled = instrctions.toString();
        final String[] uncompiled = compiled.split(" ");

        final Matcher matcher = pattern.matcher(compiled);

        final List<AbstractInsnNode[]> matches = new ArrayList<>();

        while (matcher.find()) {
            final int start = getIndex(matcher.start(), uncompiled);
            final int end = getIndex(matcher.end(), uncompiled);

            if (start == -1 || end == -1) {
                throw new RuntimeException("Failed to get index");
            }

            final int size = end - start;
            final AbstractInsnNode[] match = new AbstractInsnNode[size];

            for (int i = 0; i < size; i++) {
                match[i] = instrctions.get(start + i);
            }
            matches.add(match);
        }
        return matches;
    }

    /**
     * Converts a index of a string to the index of a string array
     * @param index the index of the compiled string
     * @return the index in the uncompiled string array
     */
    private static int getIndex(final int index, final String[] uncompiled) {
        if (index == 0) return index;

        int size = 0;
        for (int i = 0; i < uncompiled.length; i++) {
            if (size == index) {
                return i;
            }
            size += uncompiled[i].length() + 1;
        }

        return -1;
    }
}
