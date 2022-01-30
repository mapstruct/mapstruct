/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.lombok;

public class Address {
    private final String addressLine;

    public Address(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getAddressLine() {
        return addressLine;
    }
}
