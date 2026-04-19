/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class NestedSourceBean {

    private AddressBean nonNullAddress;
    private AddressBean nullableAddress;

    @NonNull
    public AddressBean getNonNullAddress() {
        return nonNullAddress;
    }

    public void setNonNullAddress(AddressBean nonNullAddress) {
        this.nonNullAddress = nonNullAddress;
    }

    @Nullable
    public AddressBean getNullableAddress() {
        return nullableAddress;
    }

    public void setNullableAddress(AddressBean nullableAddress) {
        this.nullableAddress = nullableAddress;
    }
}
