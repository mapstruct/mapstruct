/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * Decorates the assignment as an {@link java.util.Optional#get()} call.
 *
 * @author Filip Hrisafov
 */
public class OptionalGetWrapper extends AssignmentWrapper {

    private final Type optionalType;
    private final VersionInformation versionInformation;

    public OptionalGetWrapper(Assignment decoratedAssignment, Type optionalType) {
        this( decoratedAssignment, optionalType, null );
    }

    public OptionalGetWrapper(Assignment decoratedAssignment, Type optionalType,
                              VersionInformation versionInformation) {
        super( decoratedAssignment, false );
        this.optionalType = optionalType;
        this.versionInformation = versionInformation;
    }

    public Type getOptionalType() {
        return optionalType;
    }

    public VersionInformation getVersionInformation() {
        return versionInformation;
    }

    @Override
    public String toString() {
        if ( optionalType.getFullyQualifiedName().equals( "java.util.Optional" ) ) {
            if ( versionInformation != null && versionInformation.isSourceVersionAtLeast10() ) {
                return getAssignment() + ".orElseThrow()";
            }
            return getAssignment() + ".get()";
        }
        return getAssignment() + ".getAs" + Strings.capitalize( optionalType.getOptionalBaseType().getName() ) + "()";
    }
}
