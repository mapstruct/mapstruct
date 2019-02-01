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
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface CustomerEntityMapper1 {
    CustomerEntityMapper1 INSTANCE = Mappers.getMapper( CustomerEntityMapper1.class );

    @Mapping( target = ".", source = "entity" )
    CustomerEntity map( CustomerDTO customer );

    @InheritInverseConfiguration( name = "map" )
    // @Mapping(target = "entity", source = ".")
    CustomerDTO inverseMap( CustomerEntity customer );

    @Mapping( target = "entity", source = "." )
    void update( CustomerEntity customer, @MappingTarget CustomerDTO dto );

    @Mapping( target = ".", source = "entity" )
    void update( CustomerDTO dto, @MappingTarget CustomerEntity customer );

    @Mapping( target = ".", source = "customer.entity" )
    OrderEntity map( OrderDTO order );

    @Mapping(target = ".", source = "order")
    @Mapping(target = ".", source = "entity")
    SaleOrder mapDoubleTarget(SaleOrderDTO order);

    @Mapping(target = "entity", source = ".")
    OrderDTO map( OrderEntity order );

    OrderLineDTO map( OrderLine line);

    OrderLine map( OrderLineDTO line);
}
