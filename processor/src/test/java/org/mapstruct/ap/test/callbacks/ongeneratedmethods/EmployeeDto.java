/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.ongeneratedmethods;

/**
 *
 * @author Sjaak Derksen
 */
public class EmployeeDto {

    private AddressDto address;

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress( AddressDto address ) {
        this.address = address;
    }

}
