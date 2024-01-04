/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Filip Hrisafov
 */
public class MethodReferencePresenceCheck extends ModelElement implements PresenceCheck {

    protected final MethodReference methodReference;
    protected final boolean negate;

    public MethodReferencePresenceCheck(MethodReference methodReference) {
        this( methodReference, false );
    }

    public MethodReferencePresenceCheck(MethodReference methodReference, boolean negate) {
        this.methodReference = methodReference;
        this.negate = negate;
    }

    @Override
    public Set<Type> getImportTypes() {
        return methodReference.getImportTypes();
    }

    public MethodReference getMethodReference() {
        return methodReference;
    }

    public boolean isNegate() {
        return negate;
    }

    @Override
    public PresenceCheck negate() {
        return new MethodReferencePresenceCheck( methodReference, true );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        MethodReferencePresenceCheck that = (MethodReferencePresenceCheck) o;
        return Objects.equals( methodReference, that.methodReference );
    }

    @Override
    public int hashCode() {
        return Objects.hash( methodReference );
    }
}
