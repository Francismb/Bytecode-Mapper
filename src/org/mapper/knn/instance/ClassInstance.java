package org.mapper.knn.instance;

import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Project DefaultByteCodeMapperOLD
 * Created by francis on 10/19/15.
 */
public class ClassInstance extends Instance<ClassNode> {

    /**
     * The refactored name of the ClassInstance
     */
    public String name;

    /**
     * A list of FieldInstances which belong to the ClassInstance
     */
    public List<FieldInstance> fields = new ArrayList<>();

}
