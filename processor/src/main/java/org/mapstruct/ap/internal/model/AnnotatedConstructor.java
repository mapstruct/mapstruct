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
 * Represents a constructor that is used for constructor injection.
 *
 * @author Kevin Grüneberg
 */
public class AnnotatedConstructor extends ModelElement implements Constructor {

    private final String name;
    private final List<AnnotationMapperReference> mapperReferences;
    private final List<Annotation> annotations;
    private final boolean publicEmptyConstructor;

    public AnnotatedConstructor(String name, List<AnnotationMapperReference> mapperReferences,
                                List<Annotation> annotations, boolean publicEmptyConstructor) {
        this.name = name;
        this.mapperReferences = mapperReferences;
        this.annotations = annotations;
        this.publicEmptyConstructor = publicEmptyConstructor;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();

        for ( MapperReference mapperReference : mapperReferences ) {
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

    public List<AnnotationMapperReference> getMapperReferences() {
        return mapperReferences;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public boolean isPublicEmptyConstructor() {
        return publicEmptyConstructor;
    }
}
