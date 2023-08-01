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
import org.mapstruct.ap.internal.model.common.NegatePresenceCheck;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Filip Hrisafov
 */
public class SuffixPresenceCheck extends ModelElement implements PresenceCheck {

    private final String sourceReference;
    private final String suffix;
    private final boolean negate;

    public SuffixPresenceCheck(String sourceReference, String suffix) {
        this( sourceReference, suffix, false );
    }

    public SuffixPresenceCheck(String sourceReference, String suffix, boolean negate) {
        this.sourceReference = sourceReference;
        this.suffix = suffix;
        this.negate = negate;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public String getSuffix() {
        return suffix;
    }

    public boolean isNegate() {
        return negate;
    }

    @Override
    public PresenceCheck negate() {
        return new NegatePresenceCheck( this );
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
        SuffixPresenceCheck that = (SuffixPresenceCheck) o;
        return Objects.equals( sourceReference, that.sourceReference )
            && Objects.equals( suffix, that.suffix );
    }

    @Override
    public int hashCode() {
        return Objects.hash( sourceReference, suffix );
    }
}
