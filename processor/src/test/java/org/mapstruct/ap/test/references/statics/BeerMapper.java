/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.statics;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = CustomMapper.class)
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper( BeerMapper.class );

    @Mapping( target = "category", source = "percentage")
    BeerDto mapBeer(Beer beer);
}
