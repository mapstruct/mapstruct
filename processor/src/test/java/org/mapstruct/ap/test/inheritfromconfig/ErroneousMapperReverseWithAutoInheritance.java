/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(
    config = AutoInheritedConfig.class
)
public abstract class ErroneousMapperReverseWithAutoInheritance {
    public static final ErroneousMapperReverseWithAutoInheritance INSTANCE =
        Mappers.getMapper( ErroneousMapperReverseWithAutoInheritance.class );

    @Mapping( target = "colour", source = "color" )
    public abstract CarDto toCarDto(CarEntity entity);

}
