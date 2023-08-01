/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.presence;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.NegatePresenceCheck;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Filip Hrisafov
 */
public class AllPresenceChecksPresenceCheck extends ModelElement implements PresenceCheck {

    private final Collection<PresenceCheck> presenceChecks;

    public AllPresenceChecksPresenceCheck(Collection<PresenceCheck> presenceChecks) {
        this.presenceChecks = presenceChecks;
    }

    public Collection<PresenceCheck> getPresenceChecks() {
        return presenceChecks;
    }

    @Override
    public PresenceCheck negate() {
        return new NegatePresenceCheck( this );
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = new HashSet<>();
        for ( PresenceCheck presenceCheck : presenceChecks ) {
            importTypes.addAll( presenceCheck.getImportTypes() );
        }

        return importTypes;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        AllPresenceChecksPresenceCheck that = (AllPresenceChecksPresenceCheck) o;
        return Objects.equals( presenceChecks, that.presenceChecks );
    }

    @Override
    public int hashCode() {
        return Objects.hash( presenceChecks );
    }
}
