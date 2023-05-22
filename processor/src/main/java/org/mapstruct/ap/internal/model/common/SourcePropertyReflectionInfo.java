/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.EnumSet;
import java.util.Objects;

import org.mapstruct.ap.internal.util.accessor.AccessorType;

public class SourcePropertyReflectionInfo {

    private final Type containingType;
    private final String propertyName;
    private final String accessorSimpleName;
    private final boolean isMethod;

    public SourcePropertyReflectionInfo(Type containingType, String propertyName, String accessorSimpleName,
                                        AccessorType accessorType) {
        if ( !EnumSet.of( AccessorType.FIELD, AccessorType.GETTER ).contains( accessorType ) ) {
            throw new IllegalArgumentException( "Reading accessor type required, given: " + accessorType );
        }
        this.containingType = containingType;
        this.propertyName = propertyName;
        this.accessorSimpleName = accessorSimpleName;
        this.isMethod = ( accessorType == AccessorType.GETTER );
    }

    public Type getContainingType() {
        return containingType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getAccessorSimpleName() {
        return accessorSimpleName;
    }

    public boolean isMethod() {
        return isMethod;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        SourcePropertyReflectionInfo that = (SourcePropertyReflectionInfo) o;
        return isMethod == that.isMethod && containingType.equals( that.containingType ) &&
            propertyName.equals( that.propertyName ) && accessorSimpleName.equals( that.accessorSimpleName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( containingType, propertyName, accessorSimpleName, isMethod );
    }

}
