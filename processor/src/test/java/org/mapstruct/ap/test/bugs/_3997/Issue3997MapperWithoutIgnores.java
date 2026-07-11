/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3997;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.bugs._3997.Issue3997Mapper.Order;
import org.mapstruct.ap.test.bugs._3997.Issue3997Mapper.OrderDto;
import org.mapstruct.factory.Mappers;

/**
 * Same nested source paths as {@link Issue3997Mapper}, but without inverse ignores —
 * ensures parameter-prefix stripping does not break nested reverse mapping.
 *
 * @author Kamil Krzywanski
 */
@Mapper
public interface Issue3997MapperWithoutIgnores {

    Issue3997MapperWithoutIgnores INSTANCE = Mappers.getMapper( Issue3997MapperWithoutIgnores.class );

    @Mapping(target = "clientId", source = "source.client.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "city", source = "source.address.city")
    OrderDto toDto(Order source);

    @InheritInverseConfiguration
    Order toEntity(OrderDto dto);
}
