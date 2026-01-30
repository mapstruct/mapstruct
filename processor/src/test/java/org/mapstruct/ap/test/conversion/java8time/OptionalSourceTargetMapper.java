/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalSourceTargetMapper {

    String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm z";

    String LOCAL_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";

    String LOCAL_DATE_FORMAT = "dd.MM.yyyy";

    String LOCAL_TIME_FORMAT = "HH:mm";

    OptionalSourceTargetMapper INSTANCE = Mappers.getMapper( OptionalSourceTargetMapper.class );

    @Mapping(target = "zonedDateTime", dateFormat = DATE_TIME_FORMAT)
    @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT)
    @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT)
    @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    Target sourceToTarget(OptionalSource source);

    @Mapping(target = "zonedDateTime", dateFormat = DATE_TIME_FORMAT)
    @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT)
    @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT)
    @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    Target sourceToTargetDefaultMapping(OptionalSource source);

    @Mapping(target = "zonedDateTime", dateFormat = DATE_TIME_FORMAT)
    Target sourceToTargetDateTimeMapped(OptionalSource source);

    @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT)
    Target sourceToTargetLocalDateTimeMapped(OptionalSource source);

    @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT)
    Target sourceToTargetLocalDateMapped(OptionalSource source);

    @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    Target sourceToTargetLocalTimeMapped(OptionalSource source);

    @Mapping(target = "zonedDateTime", dateFormat = DATE_TIME_FORMAT)
    @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT)
    @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT)
    @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    OptionalSource targetToSource(Target target);

    @Mapping(target = "zonedDateTime", dateFormat = DATE_TIME_FORMAT)
    OptionalSource targetToSourceDateTimeMapped(Target target);

    @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT)
    OptionalSource targetToSourceLocalDateTimeMapped(Target target);

    @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT)
    OptionalSource targetToSourceLocalDateMapped(Target target);

    @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    OptionalSource targetToSourceLocalTimeMapped(Target target);

    @InheritInverseConfiguration(name = "sourceToTarget")
    OptionalSource targetToSourceDefaultMapping(Target target);
}
