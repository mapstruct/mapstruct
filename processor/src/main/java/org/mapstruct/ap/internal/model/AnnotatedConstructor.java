/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
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

    private String name;
    private final List<AnnotationMapperReference> mapperReferences;
    private final List<Annotation> annotations;
    private final NoArgumentConstructor noArgumentConstructor;
    private final Set<SupportingConstructorFragment> fragments;

    public static AnnotatedConstructor forComponentModels(String name,
                                                          List<AnnotationMapperReference> mapperReferences,
                                                          List<Annotation> annotations,
                                                          Constructor constructor,
                                                          boolean includeNoArgConstructor) {

        NoArgumentConstructor noArgumentConstructor = null;
        if ( constructor instanceof NoArgumentConstructor ) {
            noArgumentConstructor = (NoArgumentConstructor) constructor;
        }
        NoArgumentConstructor noArgConstructorToBeIncluded = null;
        Set<SupportingConstructorFragment> fragmentsToBeIncluded = Collections.emptySet();
        if ( includeNoArgConstructor ) {
            if ( noArgumentConstructor != null ) {
                noArgConstructorToBeIncluded = noArgumentConstructor;
            }
            else {
                noArgConstructorToBeIncluded = new NoArgumentConstructor( name, fragmentsToBeIncluded );
            }
        }
        else if ( noArgumentConstructor != null ) {
            fragmentsToBeIncluded = noArgumentConstructor.getFragments();
        }
        return new AnnotatedConstructor(
            name,
            mapperReferences,
            annotations,
            noArgConstructorToBeIncluded,
            fragmentsToBeIncluded
        );
    }

    private AnnotatedConstructor(String name, List<AnnotationMapperReference> mapperReferences,
                                 List<Annotation> annotations, NoArgumentConstructor noArgumentConstructor,
                                 Set<SupportingConstructorFragment> fragments) {
        this.name = name;
        this.mapperReferences = mapperReferences;
        this.annotations = annotations;
        this.noArgumentConstructor = noArgumentConstructor;
        this.fragments = fragments;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();

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

    public NoArgumentConstructor getNoArgumentConstructor() {
        return noArgumentConstructor;
    }

    public Set<SupportingConstructorFragment> getFragments() {
        return fragments;
    }
}
