/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
    config = MapConfig.class,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface CustomerMapper3 {
    CustomerMapper3 INSTANCE = Mappers.getMapper( CustomerMapper3.class );

    @Mapping( target = ".", source = "animal" )
    @Mapping( target = "color", ignore = true )
    Dog map( DogDTO dog );

    @InheritInverseConfiguration( name = "map" )
    DogDTO map( Dog dog );
}
