/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uuid;

/**
 * @author Jason Bodnar
 */
public class UUIDTarget {
    private String uuidA;
    private String invalidUUID;

    public String getUUIDA() {
        return this.uuidA;
    }

    public void setUUIDA(final String uuidA) {
        this.uuidA = uuidA;
    }

    public String getInvalidUUID() {
        return this.invalidUUID;
    }

    public void setInvalidUUID(final String invalidUUID) {
        this.invalidUUID = invalidUUID;
    }
}
