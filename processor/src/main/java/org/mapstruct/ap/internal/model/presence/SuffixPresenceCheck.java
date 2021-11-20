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
public class SuffixPresenceCheck extends ModelElement implements PresenceCheck {

    private final String sourceReference;
    private final String suffix;

    public SuffixPresenceCheck(String sourceReference, String suffix) {
        this.sourceReference = sourceReference;
        this.suffix = suffix;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public String getSuffix() {
        return suffix;
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
