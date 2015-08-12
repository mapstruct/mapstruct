/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.defaultvalue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CountryMapper {
    public static final CountryMapper INSTANCE = Mappers.getMapper( CountryMapper.class );

    @Mappings( {
            @Mapping( target = "code", defaultValue = "DE" ),
            @Mapping( target = "id", defaultValue = "42" ),
            @Mapping( target = "zipcode", defaultValue = "1337" ),
            @Mapping( target = "region", defaultValue = "someRegion" ),
    } )
    public abstract CountryDts mapToCountryDts(CountryEntity country);

    @Mappings( {
            @Mapping( target = "code", defaultValue = "DE" ),
            @Mapping( target = "id", defaultValue = "42" ),
            @Mapping( target = "zipcode", defaultValue = "1337" ),
            @Mapping( target = "region", ignore = true )

    } )
    public abstract void mapToCountryDts(CountryDts countryDts, @MappingTarget CountryEntity country);

    @Mappings( {
            @Mapping( target = "code", defaultValue = "DE" ),
            @Mapping( target = "id", defaultValue = "42" ),
            @Mapping( target = "zipcode", defaultValue = "1337" ),
            @Mapping( target = "region", ignore = true )

    } )
    public abstract void mapToCountryDts(CountryEntity source, @MappingTarget CountryEntity target);

    protected String mapToString(Region region) {
        return ( region != null ) ? region.getCode() : null;
    }
}
