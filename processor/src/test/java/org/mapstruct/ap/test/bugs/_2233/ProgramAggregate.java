/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2233;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Filip Hrisafov
 */
public class ProgramAggregate {

    private final Collection<Program> programs;

    public ProgramAggregate(Collection<Program> programs) {
        this.programs = programs;
    }

    public Optional<Collection<Program>> getPrograms() {
        return Optional.ofNullable( programs );
    }
}
