/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.beanmapping;

import java.util.Objects;

import org.mapstruct.ap.internal.model.source.MappingOptions;

/**
 * Represents the intermediate (nesting) state of the {@link MappingOptions} in this class.
 */
public class MappingReference {

    private MappingOptions mapping;

    private TargetReference targetReference;

    private SourceReference sourceReference;

    public MappingReference(MappingOptions mapping, TargetReference targetReference, SourceReference sourceReference) {
        this.mapping = mapping;
        this.targetReference = targetReference;
        this.sourceReference = sourceReference;
    }

    public MappingOptions getMapping() {
        return mapping;
    }

    public SourceReference getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(SourceReference sourceReference) {
        this.sourceReference = sourceReference;
    }

    public TargetReference getTargetReference() {
        return targetReference;
    }

    public MappingReference popTargetReference() {
        if ( targetReference != null ) {
            TargetReference newTargetReference = targetReference.pop();
            if (newTargetReference != null ) {
                return new MappingReference(mapping, newTargetReference, sourceReference );
            }
        }
        return null;
    }

    public MappingReference popSourceReference() {
        if ( sourceReference != null ) {
            SourceReference newSourceReference = sourceReference.pop();
            if (newSourceReference != null ) {
                return new MappingReference(mapping, targetReference, newSourceReference );
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        MappingReference that = (MappingReference) o;
        return mapping.equals( that.mapping );
    }

    @Override
    public int hashCode() {
        return Objects.hash( mapping );
    }

    public boolean isValid( ) {
        boolean result = false;
        if ( targetReference.isValid() ) {
             result = sourceReference != null ? sourceReference.isValid() : true;
        }
        return result;
    }

    @Override
    public String toString() {
        String targetRefStr = targetReference.toString();
        String sourceRefStr = "null";
        if ( sourceReference != null ) {
            sourceRefStr = sourceReference.toString();
        }
        return "MappingReference {"
            + "\n    sourceReference='" + sourceRefStr + "\',"
            + "\n    targetReference='" + targetRefStr + "\',"
            + "\n}";
    }
}
