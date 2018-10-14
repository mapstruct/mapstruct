/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._855;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDemoMapper {
    OrderDemoMapper INSTANCE = Mappers.getMapper( OrderDemoMapper.class );

    @Mapping(target = "field0", dependsOn = "field2")
    @Mapping(target = "order", ignore = true)
    OrderedTarget orderedWithDependsOn(OrderedSource source);

    @Mapping(target = "order", ignore = true)
    OrderedTarget orderedWithoutDependsOn(OrderedSource source);
}
