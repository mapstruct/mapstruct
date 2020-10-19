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
public class ProgramResponseDto {

    private final Collection<ProgramDto> programs;

    public ProgramResponseDto(Collection<ProgramDto> programs) {
        this.programs = programs;
    }

    public Optional<Collection<ProgramDto>> getPrograms() {
        return Optional.ofNullable( programs );
    }
}
