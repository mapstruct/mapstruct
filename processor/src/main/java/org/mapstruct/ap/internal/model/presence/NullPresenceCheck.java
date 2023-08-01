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
public class NullPresenceCheck extends ModelElement implements PresenceCheck {

    private final String sourceReference;
    private final boolean negate;

    public NullPresenceCheck(String sourceReference) {
        this.sourceReference = sourceReference;
        this.negate = false;
    }

    public NullPresenceCheck(String sourceReference, boolean negate) {
        this.sourceReference = sourceReference;
        this.negate = negate;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public boolean isNegate() {
        return negate;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public PresenceCheck negate() {
        return new NullPresenceCheck( sourceReference, !negate );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        NullPresenceCheck that = (NullPresenceCheck) o;
        return Objects.equals( sourceReference, that.sourceReference );
    }

    @Override
    public int hashCode() {
        return Objects.hash( sourceReference );
    }
}
