/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.reflection;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.asSet;

public class PropertyAnnotationReflection extends ModelElement {

    private final String fieldName;
    private final Type containingType;
    private final String accessorSimpleName;
    private final boolean isMethod;
    private final Type annotationClass;
    private final Set<Type> importTypes;

    public PropertyAnnotationReflection(PropertyAnnotationReflectionField field) {
        this.fieldName = field.getVariableName();
        this.containingType = field.getReflectionInfo().getContainingType();
        this.accessorSimpleName = field.getReflectionInfo().getAccessorSimpleName();
        this.isMethod = field.getReflectionInfo().isMethod();
        this.annotationClass = field.getType();
        this.importTypes = asSet( containingType, annotationClass );
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Type getContainingType() {
        return containingType;
    }

    public String getAccessorSimpleName() {
        return accessorSimpleName;
    }

    public boolean isMethod() {
        return isMethod;
    }

    public Type getAnnotationClass() {
        return annotationClass;
    }
}
