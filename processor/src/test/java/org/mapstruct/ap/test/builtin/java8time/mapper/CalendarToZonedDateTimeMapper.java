/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.java8time.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builtin.bean.CalendarProperty;
import org.mapstruct.ap.test.builtin.java8time.bean.ZonedDateTimeProperty;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper
public interface CalendarToZonedDateTimeMapper {
    CalendarToZonedDateTimeMapper INSTANCE = Mappers.getMapper( CalendarToZonedDateTimeMapper.class );

    ZonedDateTimeProperty map(CalendarProperty source);
}
