/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.BuildInSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.BuildInTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuildInMapper {

    BuildInMapper INSTANCE = Mappers.getMapper( BuildInMapper.class );

    @Mapping(target = "publicXmlGregCalToString", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "xmlGregCalToString", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "publicStringToXmlGregCal", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "stringToXmlGregCal", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "publicCalendarToString", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "calendarToString", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "publicStringToCalendar", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "stringToCalendar", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "publicStringToXmlGregCalFormat", source = "publicStringToXmlGregCal", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "stringToXmlGregCalFormat", source = "stringToXmlGregCal", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "publicStringToCalendarFormat", source = "publicStringToCalendar", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "stringToCalendarFormat", source = "stringToCalendar", dateFormat = "dd.MM.yyyy" )
    BuildInTarget mapBuildIn(BuildInSource source);
}
