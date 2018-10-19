/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * Mapper reference which is retrieved via Annotation-based dependency injection.<br>
 * The dependency injection may vary between field and constructor injection. Thus, it is possible to define, whether to
 * include annotations on the field.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 * @author Kevin Grüneberg
 */
public class AnnotationMapperReference extends MapperReference {

    private final List<Annotation> annotations;

    private final boolean fieldFinal;

    private final boolean includeAnnotationsOnField;

    public AnnotationMapperReference(Type type, String variableName, List<Annotation> annotations, boolean isUsed,
                                     boolean fieldFinal, boolean includeAnnotationsOnField) {
        super( type, variableName, isUsed );
        this.annotations = annotations;
        this.fieldFinal = fieldFinal;
        this.includeAnnotationsOnField = includeAnnotationsOnField;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();
        types.add( getType() );

        for ( Annotation annotation : annotations ) {
            types.addAll( annotation.getImportTypes() );
        }

        return types;
    }

    public boolean isFieldFinal() {
        return fieldFinal;
    }

    public boolean isIncludeAnnotationsOnField() {
        return includeAnnotationsOnField;
    }

    public AnnotationMapperReference withNewAnnotations(List<Annotation> annotations) {
        return new AnnotationMapperReference(
            getType(),
            getVariableName(),
            annotations,
            isUsed(),
            isFieldFinal(),
            isIncludeAnnotationsOnField() );
    }
}
