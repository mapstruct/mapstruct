/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.ongeneratedmethods;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

/**
 *
 * @author Sjaak Derksen
 */
public  class CompanyMapperPostProcessing  {

    @AfterMapping
    public void toAddressDto(Address address, @MappingTarget AddressDto addressDto) {
        String addressLine = address.getAddressLine();
        int seperatorIndex = addressLine.indexOf( ";" );
        addressDto.setStreet( addressLine.substring( 0, seperatorIndex ) );
        String houseNumber = addressLine.substring( seperatorIndex + 1, addressLine.length() );
        addressDto.setHouseNumber( Integer.parseInt( houseNumber ) );
    }

}
