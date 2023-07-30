/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Lucas Resch
 */
public class AnnotatedSetter extends GeneratedTypeMethod {

    private final Field field;
    private final Collection<Annotation> methodAnnotations;
    private final Collection<Annotation> parameterAnnotations;

    public AnnotatedSetter(Field field, Collection<Annotation> methodAnnotations,
                           Collection<Annotation> parameterAnnotations) {
        this.field = field;
        this.methodAnnotations = methodAnnotations;
        this.parameterAnnotations = parameterAnnotations;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = new HashSet<>( field.getImportTypes() );
        for ( Annotation annotation : methodAnnotations ) {
            importTypes.addAll( annotation.getImportTypes() );
        }

        for ( Annotation annotation : parameterAnnotations ) {
            importTypes.addAll( annotation.getImportTypes() );
        }

        return importTypes;
    }

    public Type getType() {
        return field.getType();
    }

    public String getFieldName() {
        return field.getVariableName();
    }

    public Collection<Annotation> getMethodAnnotations() {
        return methodAnnotations;
    }

    public Collection<Annotation> getParameterAnnotations() {
        return parameterAnnotations;
    }
}
