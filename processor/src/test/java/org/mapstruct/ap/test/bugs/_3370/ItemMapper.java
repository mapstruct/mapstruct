/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3370;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._3370.domain.Item;
import org.mapstruct.ap.test.bugs._3370.dto.ItemDTO;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ItemMapper {

    public static final ItemMapper INSTANCE = Mappers.getMapper( ItemMapper.class );

    public abstract Item map(ItemDTO itemDTO);
}
