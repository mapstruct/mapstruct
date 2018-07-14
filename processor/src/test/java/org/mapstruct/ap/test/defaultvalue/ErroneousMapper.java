/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultvalue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ErroneousMapper {
    public static final ErroneousMapper INSTANCE = Mappers.getMapper( ErroneousMapper.class );

    @Mappings( {
            @Mapping( target = "code", defaultValue = "DE", constant = "FOOBAR" ),
    } )
    public abstract CountryDts mapToCountryDts(CountryEntity country);
}
