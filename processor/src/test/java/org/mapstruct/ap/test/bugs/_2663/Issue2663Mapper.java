/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2663;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2663Mapper {

    Issue2663Mapper INSTANCE = Mappers.getMapper( Issue2663Mapper.class );

    Request map(RequestDto dto);

    default Nullable<Request.ChildRequest> mapJsonNullableChildren(JsonNullable<RequestDto.ChildRequestDto> dtos) {
        if (dtos.isPresent()) {
            return Nullable.of( mapChild( dtos.get() ) );
        } else {
            return Nullable.undefined();
        }
    }

    default <T> Nullable<T> jsonNullableToNullable(JsonNullable<T> jsonNullable) {
        if ( jsonNullable.isPresent() ) {
            return Nullable.of( jsonNullable.get() );
        }
        return Nullable.undefined();
    }

    Request.ChildRequest mapChild(RequestDto.ChildRequestDto dto);
}
