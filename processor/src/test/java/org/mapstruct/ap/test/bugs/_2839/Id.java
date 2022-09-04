/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2839;

import java.util.UUID;

/**
 * @author Hakan Özkan
 */
public class Id {

    private final UUID id;

    public Id() {
        this.id = UUID.randomUUID();
    }

    public Id(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
