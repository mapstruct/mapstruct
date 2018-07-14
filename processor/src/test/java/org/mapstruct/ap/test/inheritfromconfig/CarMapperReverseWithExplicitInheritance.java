/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(
    config = AutoInheritedConfig.class,
    mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT
)
public abstract class CarMapperReverseWithExplicitInheritance {
    public static final CarMapperReverseWithExplicitInheritance INSTANCE =
        Mappers.getMapper( CarMapperReverseWithExplicitInheritance.class );

    @InheritInverseConfiguration(name = "baseDtoToEntity")
    @Mapping( target = "colour", source = "color" )
    public abstract CarDto toCarDto(CarEntity entity);

}
