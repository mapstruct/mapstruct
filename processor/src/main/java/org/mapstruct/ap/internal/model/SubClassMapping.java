/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.AssignmentWrapper;
import org.mapstruct.ap.internal.model.assignment.ReturnWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents the mapping between a SubClass and its mapping target. This will be used by a {@link BeanMappingMethod}
 * that has {@link org.mapstruct.SubClassMapping} annotations applied to it. Before doing the normal mapping for that
 * method it will first check if the source object is of the sourceType if so it will use the assignment instead.
 *
 * @author Ben Zegveld
 */
public class SubClassMapping extends ModelElement {

    private final Type sourceType;
    private Type targetType;
    private Assignment assignment;
    private String sourceArgument;
    
    public SubClassMapping(Type sourceType, String sourceArgument, Type targetType, Assignment assignment) {
        this.sourceType = sourceType;
        this.sourceArgument = sourceArgument;
        this.targetType = targetType;
        this.assignment = assignment;
    }

    public Type getSourceType() {
        return sourceType;
    }

    @Override
    public Set<Type> getImportTypes() {
        return new HashSet<>( Arrays.asList( sourceType ) );
    }

    public AssignmentWrapper getAssignment() {
        return new ReturnWrapper( assignment );
    }

    public String getSourceArgument() {
        return sourceArgument;
    }

    @Override
    public boolean equals(final Object other) {
        if ( !( other instanceof SubClassMapping ) ) {
            return false;
        }
        SubClassMapping castOther = (SubClassMapping) other;
        return Objects.equals( sourceType, castOther.sourceType ) && Objects.equals( targetType, castOther.targetType );
    }

    @Override
    public int hashCode() {
        return Objects.hash( sourceType, targetType );
    }
}
