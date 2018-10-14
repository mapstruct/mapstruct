/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.builtin.bean.CalendarProperty;
import org.mapstruct.ap.test.builtin.bean.StringProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StringToCalendarMapper {

    StringToCalendarMapper INSTANCE = Mappers.getMapper( StringToCalendarMapper.class );

    @Mapping( target = "prop", dateFormat = "dd.MM.yyyy" )
    @Mapping( target = "publicProp", dateFormat = "dd.MM.yyyy" )
    CalendarProperty map( StringProperty source);
}
