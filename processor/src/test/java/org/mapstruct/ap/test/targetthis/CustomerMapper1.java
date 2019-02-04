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
public interface CustomerMapper1 {
    CustomerMapper1 INSTANCE = Mappers.getMapper( CustomerMapper1.class );

    @Mapping( target = ".", source = "item" )
    CustomerItem map(CustomerDTO customer );

    @InheritInverseConfiguration( name = "map" )
    // @Mapping(target = "item", source = ".")
    CustomerDTO inverseMap( CustomerItem customer );

    @Mapping( target = "item", source = "." )
    void update(CustomerItem customer, @MappingTarget CustomerDTO dto );

    @Mapping( target = ".", source = "item" )
    void update( CustomerDTO dto, @MappingTarget CustomerItem customer );

    @Mapping( target = ".", source = "customer.item" )
    OrderItem map(OrderDTO order );

    @Mapping(target = ".", source = "order")
    @Mapping(target = ".", source = "item")
    SaleOrder mapDoubleTarget(SaleOrderDTO order);

    @Mapping(target = "item", source = ".")
    OrderDTO map( OrderItem order );

    OrderLineDTO map( OrderLine line);

    OrderLine map( OrderLineDTO line);
}
