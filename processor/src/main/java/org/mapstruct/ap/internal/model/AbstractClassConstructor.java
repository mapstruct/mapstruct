/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents a constructor compatible with an abstract superclass
 *
 * @author Marvin Froeder
 */
public class AbstractClassConstructor extends ModelElement implements Constructor {

    private final String name;
    private final List<MapperReference> superclassParameters;
    private final List<MapperReference> extraParameters;
    private final List<Annotation> annotations;

    public AbstractClassConstructor(String name, List<MapperReference> superclassParameters,
                                    List<MapperReference> extraParameters, List<Annotation> annotations) {
        this.name = name;
        this.superclassParameters = superclassParameters;
        this.extraParameters = extraParameters;
        this.annotations = annotations;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();

        for ( MapperReference mapperReference : superclassParameters ) {
            types.addAll( mapperReference.getImportTypes() );
        }

        for ( MapperReference mapperReference : extraParameters ) {
            types.addAll( mapperReference.getImportTypes() );
        }

        for ( Annotation annotation : annotations ) {
            types.addAll( annotation.getImportTypes() );
        }

        return types;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<MapperReference> getSuperclassParameters() {
        return superclassParameters;
    }

    public List<MapperReference> getExtraParameters() {
        return extraParameters;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }
}
