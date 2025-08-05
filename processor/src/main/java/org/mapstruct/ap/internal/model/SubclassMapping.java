/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.AssignmentWrapper;
import org.mapstruct.ap.internal.model.assignment.ReturnWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents the mapping between a Subclass and its mapping target. This will be used by a {@link BeanMappingMethod}
 * that has {@link org.mapstruct.SubclassMapping} annotations applied to it. Before doing the normal mapping for that
 * method it will first check if the source object is of the sourceType if so it will use the assignment instead.
 *
 * @author Ben Zegveld
 */
public class SubclassMapping extends ModelElement {

    private final Type sourceType;
    private final Type targetType;
    private final Assignment assignment;
    private final String sourceArgument;
    private final Type sourceArgumentType;

    public SubclassMapping(Type sourceType, String sourceArgument, Type sourceArgumentType,
                           Type targetType, Assignment assignment) {
        this.sourceType = sourceType;
        this.sourceArgument = sourceArgument;
        this.sourceArgumentType = sourceArgumentType;
        this.targetType = targetType;
        this.assignment = assignment;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public Type getSourceArgumentType() {
        return sourceArgumentType;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.singleton( sourceType );
    }

    public AssignmentWrapper getAssignment() {
        return new ReturnWrapper( assignment );
    }

    public String getSourceArgument() {
        return sourceArgument;
    }

    @Override
    public boolean equals(final Object other) {
        if ( !( other instanceof SubclassMapping ) ) {
            return false;
        }
        SubclassMapping castOther = (SubclassMapping) other;
        return Objects.equals( sourceType, castOther.sourceType ) && Objects.equals( targetType, castOther.targetType );
    }

    @Override
    public int hashCode() {
        return Objects.hash( sourceType, targetType );
    }
}
