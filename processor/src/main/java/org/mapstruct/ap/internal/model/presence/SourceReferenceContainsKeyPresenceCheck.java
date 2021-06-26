/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.presence;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Filip Hrisafov
 */
public class SourceReferenceContainsKeyPresenceCheck extends ModelElement implements PresenceCheck {

    private final String sourceReference;
    private final String propertyName;

    public SourceReferenceContainsKeyPresenceCheck(String sourceReference, String propertyName) {
        this.sourceReference = sourceReference;
        this.propertyName = propertyName;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        SourceReferenceContainsKeyPresenceCheck that = (SourceReferenceContainsKeyPresenceCheck) o;
        return Objects.equals( sourceReference, that.sourceReference ) &&
            Objects.equals( propertyName, that.propertyName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( sourceReference, propertyName );
    }
}
