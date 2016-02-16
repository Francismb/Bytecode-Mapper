package org;

import org.objectweb.asm.tree.analysis.Analyzer;
import org.mapper.io.archive.ArchiveRetriever;
import old_src.XMLExporter;
import org.mapper.knn.FeatureSpace;
import org.mapper.knn.factory.FeatureFactory;
import org.mapper.knn.instance.ClassInstance;
import org.mapper.refactor.Refactorizer;
import org.mapper.requisite.CodeResourcer;
import org.mapper.requisite.CodeTransformer;
import org.mapper.utilities.Utilities;
import org.objectweb.asm.tree.*;
import old_src.XMLReader;
import org.mapper.knn.Feature;
import org.mapper.knn.factory.ClassFeatures;
import org.mapper.knn.factory.FieldFeatures;
import org.mapper.knn.instance.FieldInstance;
import org.mapper.utilities.Action;

import java.io.*;
import java.util.*;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 */
public class DefaultByteCodeMapperOLD {

    /**
     * The action decides how the updater runs.
     * See {@link Action} for the possible actions.
     */
    private final Action action;

    /**
     * The path to the pattern file which is loaded for run actions.
     * The pattern file is overwritten with the new data at the end of a run action.
     */
    private final String patternPath;

    /**
     * Generate method frames, this is useful for deobfuscation and finding resources.
     */
    private boolean generateFrames;

    /**
     * A map of the classes loaded from {link #getClassRetriever()}.
     */
    private Map<String, ClassNode> classes = new HashMap<>();

    /**
     * The computed instances of classes and fields.
     */
    private List<ClassInstance> computedInstances;

    /**
     * Output switch
     */
    private boolean output = false;

    /**
     * A refactor/dump switch
     */
    private boolean refactor = false;

    /**
     * The name of the dump file
     */
    private String refactorFile = "dump.jar";

    /**
     * A list of field features.
     * Add a custom feature and implement the value
     * of the feature in the xml to add a feature to knn.
     */
    public List<FeatureFactory<FieldNode>> fieldFeatureFactories = new ArrayList<FeatureFactory<FieldNode>>() {
        {
            add(FieldFeatures.TYPES);
            add(FieldFeatures.REFERENCES);
            add(FieldFeatures.MULTIPLICATIONS);
            add(FieldFeatures.ADDITIONS);
            add(FieldFeatures.SUBTRACTIONS);
        }
    };

    /**
     * A list of class features.
     * Add a custom feature and implement the value
     * of the feature in the xml to add a feature to knn.
     */
    public List<FeatureFactory<ClassNode>> classFeatureFactories = new ArrayList<FeatureFactory<ClassNode>>() {
        {
            add(ClassFeatures.ARRAY1);
            add(ClassFeatures.ARRAY2);
            add(ClassFeatures.ARRAY3);
            add(ClassFeatures.BOOLEAN);
            add(ClassFeatures.BYTE);
            add(ClassFeatures.CHAR);
            add(ClassFeatures.DOUBLE);
            add(ClassFeatures.FLOAT);
            add(ClassFeatures.INTEGER);
            add(ClassFeatures.LONG);
            add(ClassFeatures.SHORT);
            add(ClassFeatures.OBJECT);
            add(ClassFeatures.FIELDS);
            add(ClassFeatures.METHODS);
            add(ClassFeatures.INHERITANCE);
            add(ClassFeatures.INTERFACE);
            add(ClassFeatures.SUPER);
        }
    };

    /**
     * @param action      the action for the updater to execute.
     * @param patternPath the path to the pattern file.
     */
    public DefaultByteCodeMapperOLD(final Action action, final String patternPath) {
        this(action, patternPath, false);
    }

    /**
     * @param action         the action for the updater to execute.
     * @param patternPath    the path to the pattern file.
     * @param generateFrames a flag to enable the generation of frames
     */
    public DefaultByteCodeMapperOLD(final Action action, final String patternPath, final boolean generateFrames) {
        this.action = action;
        this.patternPath = patternPath;
        this.generateFrames = generateFrames;
    }

    /**
     * Runs the updater corresponding to the action supplied
     */
    public final void run() {
        init();
        if (action.update) update();
        if (action.analyze) analyse();
    }

    /**
     * @return the computed instances of classes/fields from knn
     */
    public List<ClassInstance> getComputedInstances() {
        return this.computedInstances;
    }

    /**
     * Sets the output value which decides whether
     * to output debug info to the console or not
     *
     * @param output
     */
    public void setOutput(final boolean output) {
        this.output = output;
    }

    /**
     * Sets the dump value which decides wheather
     * or not to refactor a jar file and dump it.
     *
     * @param value
     */
    public void setRefactor(final boolean value) {
        this.refactor = value;
    }

    public void setRefactorFile(final String name) {
        this.refactorFile = name;
    }

    public Map<String, ClassNode> getClasses() {
        return classes;
    }

    /**
     * Adds a field feature factory to the list of field feature factories
     *
     * @param factory a field feature factory
     */
    public void addFieldFeatureFactory(final FeatureFactory<FieldNode> factory) {
        fieldFeatureFactories.add(factory);
    }

    /**
     * Adds a class feature factory to the list of class feature factories
     *
     * @param factory a class feature factory
     */
    public void addClassFeatureFactory(final FeatureFactory<ClassNode> factory) {
        classFeatureFactories.add(factory);
    }

    /**
     * Allows the updater to generate the frames
     *
     * @param generateFrames
     */
    public void setFrameGeneration(final boolean generateFrames) {
        this.generateFrames = generateFrames;
    }

    /**
     * Initializes the updater, loads the classes, runs the transformers
     * and resourcers and initializes the metrics to be used in the feature space
     */
    private void init() {
        log("Running updater");
        /**
         * Load the classes of the game
         *
         * If the action is a get then download a fresh copy
         * of the classes else use the local copy
         */
        log("\nLoading class files");
        final ArchiveRetriever retriever = getClassRetriever();
        classes = action.get ? retriever.update() : retriever.local();
        log("Loaded " + classes.size() + " class files");

        /**
         * Generate and cache method frames
         */
        if (generateFrames) {
            log("\nGenerating and caching method frames");
            Analyzer.generateCache(classes);
            log("Finished generating and caching frames for " + org.objectweb.asm.tree.analysis.Analyzer.cache.size() + " methods");
        }

        /**
         * Transform the classes
         */
        final CodeTransformer[] transformers = getTransformers();
        log("\nExecuting " + transformers.length + " transformers");
        for (final CodeTransformer transformer : transformers) {
            transformer.transform(classes);
        }
        log("Executed " + transformers.length + " transformers");

        /**
         * Regenerate and cache method frames
         */
        if (generateFrames) {
            log("\nRegenerating and caching method frames");
            Analyzer.generateCache(classes);
            log("Finished regenerating and caching frames for " + org.objectweb.asm.tree.analysis.Analyzer.cache.size() + " methods");
        }

        /**
         * Acquire the resources from the classes
         */
        final CodeResourcer[] resources = getResourcers();
        log("\nExecuting " + resources.length + " resource gatherers");
        for (final CodeResourcer resourcer : getResourcers()) {
            resourcer.search(classes);
        }
        log("Executed " + resources.length + " resources gatherers");

        /**
         * Initialize the knn features
         */
        log("\nCreating features");
        for (final FeatureFactory<ClassNode> classFactory : classFeatureFactories) {
            classFactory.init(classes);
        }
        for (final FeatureFactory<FieldNode> fieldFactory : fieldFeatureFactories) {
            fieldFactory.init(classes);
        }
        log("Created " + (classes.size() * classFeatureFactories.size()) + " class features");
        log("Created " + fieldFeatureFactories.size() * fieldFeatureFactories.get(0).features.size() + " field features");
    }

    /**
     * Runs the update action.
     * Computes all patterns in a knn feature space and outputs
     * the new feature data to the pattern file
     */
    private void update() {
        /**
         * Initialize the Class Feature space
         */
        log("\nInitialising feature space");
        final Feature<ClassNode>[][] classFeatures = new Feature[classFeatureFactories.size()][classes.size()];
        for (int i = 0; i < classFeatureFactories.size(); i++) {
            classFeatures[i] = classFeatureFactories.get(i).produce();
        }
        final FeatureSpace<ClassNode> classSpace = new FeatureSpace<>(classFeatures);
        log("Initialised feature space");

        /**
         * Load the class and field patterns from xml
         */
        log("\nLoading instance patterns");
        computedInstances = XMLReader.read(patternPath);
        log("Loaded instance patterns");

        /**
         * Compute all the class instances to find the classifications
         */
        for (final ClassInstance classInstance : computedInstances) {

            classSpace.compute(classInstance);
            log(Utilities.createOutput(classInstance));

            if (classInstance.fields.size() > 0) {

                /**
                 * Initialize a field feature space with local fields
                 */
                final Feature<FieldNode>[][] fieldFeatures = new Feature[fieldFeatureFactories.size()][classInstance.category.fields.size()];
                for (int i = 0; i < fieldFeatureFactories.size(); i++) {
                    fieldFeatures[i] = fieldFeatureFactories.get(i).produce(FieldFeatures.hash(classInstance.category, classInstance.category.fields));
                }
                if (fieldFeatures.length == 0) {
                    continue;
                }
                final FeatureSpace<FieldNode> fieldSpace = new FeatureSpace<>(fieldFeatures);
                for (final FieldInstance fieldInstance : classInstance.fields) {
                    /**
                     * Compute the field instance
                     */
                    fieldSpace.compute(fieldInstance);
                    log(Utilities.createOutput(fieldInstance));
                }
            }
        }

        /**
         * Export the new classified instances to xml
         */
        log("\nDumping updated patterns to \"" + patternPath + "\"");
        XMLExporter.export(patternPath, computedInstances);

        /**
         * Refactor a jar file and dump it
         */
        if (refactor) {
            log("Dumping refactored jar to \"" + refactorFile + "\"\n");
            final Refactorizer refactorizer = new Refactorizer(refactorFile, classes, computedInstances);
            refactorizer.refactor();
        }
    }

    private void analyse() {
        String line;
        final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            while ((line = input.readLine()) != null) {
                final String[] split = line.split("\\.");
                final String className = split.length > 0 && !split[0].isEmpty() ? split[0] : null;
                final String fieldName = split.length > 1 && !split[1].isEmpty() ? split[1] : null;
                if (className != null) {
                    if (fieldName != null) {
                        final Feature<FieldNode>[] features = new Feature[fieldFeatureFactories.size()];
                        for (int i = 0; i < fieldFeatureFactories.size(); i++) {
                            features[i] = fieldFeatureFactories.get(i).produce(className + ":" + fieldName);
                        }
                        String output = "Pattern = ";
                        for (int i = 0; i < features.length; i++) {
                            final Feature<FieldNode> feature = features[i];
                            output += feature.attribute.key + "=" + feature.attribute.value + ",";
                        }
                        log(output);
                    } else {
                        final Feature<ClassNode>[] features = new Feature[classFeatureFactories.size()];
                        for (int i = 0; i < classFeatureFactories.size(); i++) {
                            features[i] = classFeatureFactories.get(i).produce(className);
                        }
                        String output = "Pattern = ";
                        for (int i = 0; i < features.length; i++) {
                            final Feature<ClassNode> feature = features[i];
                            output += feature.attribute.key + "=" + feature.attribute.value + ",";
                        }
                        log(output);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(final String message) {
        if (output) System.out.println(message);
    }
}
