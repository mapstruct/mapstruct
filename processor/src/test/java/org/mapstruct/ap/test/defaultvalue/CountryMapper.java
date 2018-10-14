/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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

    @Mapping( target = "code", defaultValue = "DE" )
    @Mapping( target = "id", defaultValue = "42" )
    @Mapping( target = "zipcode", defaultValue = "1337" )
    @Mapping( target = "region", defaultValue = "someRegion" )
    @Mapping( target = "continent", defaultValue = "EUROPE" )
    public abstract CountryDts mapToCountryDts(CountryEntity country);

    @Mapping( target = "code", defaultValue = "DE" )
    @Mapping( target = "id", defaultValue = "42" )
    @Mapping( target = "zipcode", defaultValue = "1337" )
    @Mapping( target = "region", ignore = true )
    @Mapping( target = "continent", defaultValue = "EUROPE" )
    public abstract void mapToCountryEntity(CountryDts countryDts, @MappingTarget CountryEntity country);

    @Mapping( target = "code", defaultValue = "DE" )
    @Mapping( target = "id", defaultValue = "42" )
    @Mapping( target = "zipcode", defaultValue = "1337" )
    @Mapping( target = "region", ignore = true )
    @Mapping( target = "continent", defaultValue = "EUROPE" )
    public abstract void mapToCountryEntity(CountryEntity source, @MappingTarget CountryEntity target);

    protected String mapToString(Region region) {
        return ( region != null ) ? region.getCode() : null;
    }
}
