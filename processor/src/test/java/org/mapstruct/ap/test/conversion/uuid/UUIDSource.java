/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uuid;

import java.util.UUID;

/**
 * @author Jason Bodnar
 */
public class UUIDSource {
    private UUID uuidA;

    private UUID invalidUUID;

    public UUID getUUIDA() {
        return this.uuidA;
    }

    public void setUUIDA(final UUID uuidA) {
        this.uuidA = uuidA;
    }

    public UUID getInvalidUUID() {
        return invalidUUID;
    }

    public void setInvalidUUID(final UUID invalidUUID) {
        this.invalidUUID = invalidUUID;
    }
}
