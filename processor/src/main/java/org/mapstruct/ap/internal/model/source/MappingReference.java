/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Objects;

import org.mapstruct.ap.internal.util.Strings;

/**
 * Represents the intermediate (nesting) state of the {@link Mapping} in this class.
 */
public class MappingReference {

    private Mapping mapping;

    private TargetReference targetReference;

    private SourceReference sourceReference;

    public MappingReference(Mapping mapping, TargetReference targetReference, SourceReference sourceReference) {
        this.mapping = mapping;
        this.targetReference = targetReference;
        this.sourceReference = sourceReference;
    }

    public Mapping getMapping() {
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

    @Override
    public String toString() {
        String targetRefName = Strings.join( targetReference.getElementNames(), "." );
        String sourceRefName = "null";
        if ( sourceReference != null ) {
            sourceRefName = Strings.join( sourceReference.getElementNames(), "." );
        }
        return "MappingReference {"
            + "\n    sourceRefName='" + sourceRefName + "\',"
            + "\n    targetRefName='" + targetRefName + "\',"
            + "\n}";
    }
}
