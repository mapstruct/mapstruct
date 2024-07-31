/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * This class represents constructor that accepts all fields of a Mapper and sets their values
 * for implementation of a Mapper it calls its super constructor
 */
public class CanonicalConstructor extends ModelElement implements Constructor {

    private final String name;
    private final List<ConstructorParameter> parameters;
    private boolean includeNoArgConstructor;
    private List<Annotation> annotations;
    private final TypeFactory typeFactory;

    public CanonicalConstructor(String name, List<ConstructorParameter> parameters, boolean includeNoArgConstructor,
                                TypeFactory typeFactory) {
        this.name = name;
        this.parameters = parameters;
        this.includeNoArgConstructor = includeNoArgConstructor;
        this.typeFactory = typeFactory;
        this.annotations = Collections.emptyList();
    }

    @Override
    public String getName() {
        return name;
    }

    public List<ConstructorParameter> getParameters() {
        return parameters;
    }

    public boolean shouldIncludeNoArgConstructor() {
        return includeNoArgConstructor;
    }

    public void setIncludeNoArgConstructor(boolean includeNoArgConstructor) {
        this.includeNoArgConstructor = includeNoArgConstructor;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = Stream.concat( parameters.stream(), annotations.stream() )
            .flatMap( type -> type.getImportTypes().stream() )
            .collect( Collectors.toSet() );

        if ( includeNoArgConstructor && parameters.stream().anyMatch( ConstructorParameter::isAnnotatedMapper ) ) {
            importTypes.add( typeFactory.getType( "org.mapstruct.factory.Mappers" ) );
        }
        return importTypes;
    }

}
