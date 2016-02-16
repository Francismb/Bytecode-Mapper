package org.mapper;

import org.mapper.io.archive.ArchiveRetriever;
import org.mapper.requisite.CodeResourcer;
import org.mapper.requisite.CodeTransformer;
import org.mapper.utilities.Action;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.analysis.Analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Project ByteCodeMapper
 * Created by Francis on 30/11/2015.
 *
 * Handles the initial setup of the bytecode mapper including loading classes,
 * transforming/deobfuscation and locating resources.
 */
public abstract class DefaultHandler implements PatternCreator, PatternMapper {

    /**
     * The {@link ArchiveRetriever} to be used to load classes
     */
    private final ArchiveRetriever archiveRetriever;

    /**
     * A {@link Set} of {@link CodeResourcer} to be executed by the handle function
     */
    private final Set<CodeResourcer> resourcers = new HashSet<>();

    /**
     * A {@link Set} of {@link CodeTransformer} to be executed by the handle function
     */
    private final Set<CodeTransformer> transformers = new HashSet<>();

    /**
     * A {@link Map} of the loaded classes
     */
    public Map<String, ClassNode> classes = new HashMap<>();

    /**
     * Constructs a new {@link DefaultHandler}.
     *
     * @param archiveRetriever the {@link ArchiveRetriever} to be used to load classes.
     */
    public DefaultHandler(final ArchiveRetriever archiveRetriever) {
        this.archiveRetriever = archiveRetriever;
    }

    /**
     * Handles the initial setup of the bytecode mapper.
     *
     * @param action The action to run the handle function in.
     * @param generateFrames Generate frames before and after transforming classes.
     */
    public void handle(final Action action, final boolean generateFrames) {
        /**
         * Load the classes of the game.
         *
         * If the action is a get then download a fresh copy
         * of the classes else use the local copy.
         */
        classes = action.get ? archiveRetriever.update() : archiveRetriever.local();

        /**
         * If @param generateFrames is true then we will generate
         * bytecode frames for the transformer.
         */
        if (generateFrames) {
            Analyzer.generateCache(classes);
        }

        /**
         * Transform the classes
         */
        for (final CodeTransformer transformer : transformers) {
            transformer.transform(classes);
        }

        /**
         * If @param generateFrames is true then we re-generate
         * the frames as the transformers probably have changed the bytecode.
         */
        if (generateFrames) {
            Analyzer.generateCache(classes);
        }

        /**
         * Acquire the resources from the classes.
         */
        for (final CodeResourcer resourcer : resourcers) {
            resourcer.search(classes);
        }
    }

    /**
     * Add a course resourcer which will be executed by the handle function.
     *
     * @param resourcer the resourcer to be added.
     */
    public void addCodeResourcer(final CodeResourcer resourcer) {
        resourcers.add(resourcer);
    }

    /**
     * Add a course transformer which will be executed by the handle function.
     *
     * @param resourcer the transformer to be added.
     */
    public void addCodeTransformer(final CodeResourcer resourcer) {
        resourcers.add(resourcer);
    }

}
