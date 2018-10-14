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
public abstract class ErroneousMapper2 {
    public static final ErroneousMapper2 INSTANCE = Mappers.getMapper( ErroneousMapper2.class );

    @Mapping( target = "code", defaultValue = "DE", expression = "java(;)" )
    public abstract CountryDts mapToCountryDts(CountryEntity country);
}
