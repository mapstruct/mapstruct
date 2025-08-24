/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.fluent.getters;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.fluent.getters.domain.Item;
import org.mapstruct.ap.test.fluent.getters.dto.ItemDto;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder)
public abstract class ItemMapper {

    public static final ItemMapper INSTANCE = Mappers.getMapper( ItemMapper.class );

    public abstract Item map(ItemDto itemDTO);

}
