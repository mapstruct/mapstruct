/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.decorator.nested;

/**
 *
 * @author Sjaak Derksen
 */
public abstract class CompanyMapperDecorator implements CompanyMapper {

    private final CompanyMapper delegate;

    public CompanyMapperDecorator( CompanyMapper delegate ) {
        this.delegate = delegate;
    }

    @Override
    public AddressDto toAddressDto(Address address) {
        AddressDto addressDto = delegate.toAddressDto( address );
        String addressLine = address.getAddressLine();
        int seperatorIndex = addressLine.indexOf( ";" );
        addressDto.setStreet( addressLine.substring( 0, seperatorIndex - 1 ) );
        String houseNumber = addressLine.substring( seperatorIndex + 1, addressLine.length() );
        addressDto.setHouseNumber( Integer.getInteger( houseNumber ) );
        return addressDto;
    }

}
