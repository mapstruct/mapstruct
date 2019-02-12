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
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CustomerMapper2 {
    CustomerMapper2 INSTANCE = Mappers.getMapper( CustomerMapper2.class );

    @Mapping( target = "." )
    Item map(ItemDTO item );

    @Mapping( target = "." )
    @Mapping( target = "." )
    CustomerItem map(CustomerDTO customer );

    @Mapping( target = "name" )
    CustomerItem mapNameOnly(CustomerDTO customer );

    @InheritInverseConfiguration( name = "map" )
    CustomerDTO inverseMap( CustomerItem customer );

}
