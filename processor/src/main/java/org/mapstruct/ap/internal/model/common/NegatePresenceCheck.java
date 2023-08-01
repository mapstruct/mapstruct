/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Objects;
import java.util.Set;

/**
 * @author Filip Hrisafov
 */
public class NegatePresenceCheck extends ModelElement implements PresenceCheck {

    private final PresenceCheck presenceCheck;

    public NegatePresenceCheck(PresenceCheck presenceCheck) {
        this.presenceCheck = presenceCheck;
    }

    public PresenceCheck getPresenceCheck() {
        return presenceCheck;
    }

    @Override
    public Set<Type> getImportTypes() {
        return presenceCheck.getImportTypes();
    }

    @Override
    public PresenceCheck negate() {
        return presenceCheck;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        NegatePresenceCheck that = (NegatePresenceCheck) o;
        return Objects.equals( presenceCheck, that.presenceCheck );
    }

    @Override
    public int hashCode() {
        return Objects.hash( presenceCheck );
    }
}
