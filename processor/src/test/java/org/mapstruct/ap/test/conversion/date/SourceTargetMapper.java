/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.date;

import java.util.Date;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping(target = "date", dateFormat = "dd.MM.yyyy")
    Target sourceToTarget(Source source);

    @InheritInverseConfiguration
    Source targetToSource(Target target);

    @IterableMapping(dateFormat = "dd.MM.yyyy")
    List<String> stringListToDateList(List<Date> dates);

    @IterableMapping(dateFormat = "dd.MM.yyyy")
    String[] stringListToDateArray(List<Date> dates);

    @InheritInverseConfiguration
    List<Date> dateListToStringList(List<String> strings);

    @InheritInverseConfiguration
    List<Date> stringArrayToDateList(String[] dates);

    @IterableMapping(dateFormat = "dd.MM.yyyy")
    String[] dateArrayToStringArray(Date[] dates);

    @InheritInverseConfiguration
    Date[] stringArrayToDateArray(String[] dates);
}
