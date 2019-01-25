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
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface CustomerEntityMapper2 {
    CustomerEntityMapper2 INSTANCE = Mappers.getMapper( CustomerEntityMapper2.class );

    @Mapping( target = "." )
    Entity map( EntityDTO entity );

    @Mapping( target = "." )
    @Mapping( target = "." )
    CustomerEntity map( CustomerDTO customer );

    @Mapping( target = "name" )
    CustomerEntity mapNameOnly( CustomerDTO customer );

    @InheritInverseConfiguration( name = "map" )
    CustomerDTO inverseMap( CustomerEntity customer );

}
