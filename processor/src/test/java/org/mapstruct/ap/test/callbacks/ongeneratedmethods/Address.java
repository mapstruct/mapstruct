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
public class Address {

    private String addressLine;
    private String town;

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine( String addressLine ) {
        this.addressLine = addressLine;
    }

    public String getTown() {
        return town;
    }

    public void setTown( String town ) {
        this.town = town;
    }
}
