/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;

public class UnmappableConstructorTargetBean {

    private final AddressBean payload;

    public UnmappableConstructorTargetBean(@NonNull AddressBean payload) {
        this.payload = payload;
    }

    public AddressBean getPayload() {
        return payload;
    }
}
