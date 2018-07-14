/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.statics;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.references.statics.nonused.NonUsedMapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses =  NonUsedMapper.class )
public abstract class BeerMapperWithNonUsedMapper {

    public static final BeerMapperWithNonUsedMapper INSTANCE = Mappers.getMapper( BeerMapperWithNonUsedMapper.class );

    @Mapping( target = "category", source = "percentage")
    public abstract BeerDto mapBeer(Beer beer);

    public static Category toCategory(float in) {

        if ( in < 2.5 ) {
            return Category.LIGHT;
        }
        else if ( in < 5.5 ) {
            return Category.LAGER;
        }
        else if ( in < 10 ) {
            return Category.STRONG;
        }
        else {
            return Category.BARLEY_WINE;
        }
    }
}
