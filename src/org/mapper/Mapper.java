package org.mapper;

import org.mapper.identity.ClassIdentity;
import org.mapper.identity.FieldIdentity;
import org.mapper.knn.Feature;
import org.mapper.knn.FeatureSpace;
import org.mapper.knn.producer.FeatureProducer;
import org.mapper.knn.producer.classtype.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Francis on 4/06/16.
 * Project Bytecode-Mapper.
 */
public class Mapper {

    /**
     * The classes that this {@link Mapper} was constructed with.
     */
    private final Map<String, ClassNode> classes;

    /**
     * The class feature space which is constructed when the compute
     * function is called.
     */
    private FeatureSpace<ClassNode> classFeatureSpace;

    /**
     * The static field feature space is constructed when the compute
     * function is called.
     */
    private FeatureSpace<ClassNode> staticFieldFeatureSpace;

    /**
     * A list of {@link FeatureProducer} which generate features/metrics
     * which are used to classify fields or classes.
     */
    public static final List<FeatureProducer<ClassNode>> classFeatureProducers = new ArrayList() {
        {
            add(new BooleanFeatureProducer());
            add(new ByteFeatureProducer());
            add(new CharacterFeatureProducer());
            add(new FloatFeatureProducer());
            add(new IntegerFeatureProducer());
            add(new LongFeatureProducer());
            add(new ObjectFeatureProducer());
            add(new ShortFeatureProducer());
            add(new SingleDimensionArrayFeatureProducer());
            add(new DoubleDimensionArrayFeatureProducer());
            add(new TrippleDimensionArrayFeatureProducer());
            add(new FieldsFeatureProducer());
            add(new MethodsFeatureProducer());
            add(new InheritanceFeatureProducer());
            add(new InterfaceFeatureProducer());
            add(new SuperFeatureProducer());
        }
    };

    /**
     * Constructs a new {@link Mapper}.
     * @param classes is the map of classes used in the feature space.
     */
    public Mapper(final Map<String, ClassNode> classes) {
        this.classes = classes;
    }

    /**
     * Pre-calculates the features and constructs a class feature space.
     */
    public void compute() {
        for (final ClassNode classNode : classes.values()) {
            for (final FeatureProducer<ClassNode> featureProducer : classFeatureProducers) {
                featureProducer.visitClassNode(classNode);
            }
            for (final FieldNode fieldNode : classNode.fields) {
                for (final FeatureProducer<ClassNode> featureProducer : classFeatureProducers) {
                    featureProducer.visitFieldNode(classNode, fieldNode);
                }
            }
        }
        for (final FeatureProducer<ClassNode> featureProducer : classFeatureProducers) {
            featureProducer.visitClassNodeMap(classes);
        }
        final Feature<ClassNode>[][] classFeatures = new Feature[classFeatureProducers.size()][classes.size()];
        for (int i = 0; i < classFeatures.length; i++) {
            classFeatures[i] = classFeatureProducers.get(i).produce();
        }
        this.classFeatureSpace = new FeatureSpace<>(classFeatures);
    }

    /**
     * Classifies a {@link ClassIdentity}, it will set the instance,
     * distance and feature fields of the {@link org.mapper.identity.Identity}.
     * @param classIdentity
     * @return
     */
    public void classify(final ClassIdentity classIdentity) {
        classFeatureSpace.compute(classIdentity.instance, true);
    }

    public void classify(final FieldInsnNode fieldIdentity) {

    }

    public void  classify(final ClassIdentity classIdentity, final FieldIdentity[] fieldIdentity) {

    }
}
