/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3708;

import org.mapstruct.BeanMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ap.test.bugs._3708.dto.PartnerDto;
import org.mapstruct.ap.test.bugs._3708.entity.Partner;
import org.mapstruct.factory.Mappers;

/**
 * BeanMapping ACCESSOR_ONLY
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY)
public interface Issue3708AccessorOnlyMapper {
    Issue3708AccessorOnlyMapper INSTANCE = Mappers.getMapper( Issue3708AccessorOnlyMapper.class );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateBeanMapping(@MappingTarget Partner entity, PartnerDto dto);
}
