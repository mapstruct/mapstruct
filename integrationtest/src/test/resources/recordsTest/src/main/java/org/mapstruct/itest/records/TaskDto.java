/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records;

import java.util.List;

/**
 * @author Oliver Erhart
 */
public record TaskDto(String id, Long number) {

    @Default
    TaskDto(String id) {
        this( id, 1L );
    }

}
