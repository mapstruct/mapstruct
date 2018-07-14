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
public class AddressDto {

    private int houseNumber;
    private String street;
    private String town;

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber( int houseNumber ) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet( String street ) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown( String town ) {
        this.town = town;
    }
}
