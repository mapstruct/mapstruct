/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.erroneous;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousFormatMapper {

    @Mapping(target = "date", dateFormat = "qwertz")
    @Mapping(target = "zonedDateTime", dateFormat = "qwertz")
    @Mapping(target = "localDateTime", dateFormat = "qwertz")
    @Mapping(target = "localDate", dateFormat = "qwertz")
    @Mapping(target = "localTime", dateFormat = "qwertz")
    @Mapping(target = "dateTime", dateFormat = "qwertz")
    @Mapping(target = "jodaLocalDateTime", dateFormat = "qwertz")
    @Mapping(target = "jodaLocalDate", dateFormat = "qwertz")
    @Mapping(target = "jodaLocalTime", dateFormat = "qwertz")
    Target sourceToTarget(Source source);

    @IterableMapping(dateFormat = "qwertz")
    List<String> fromDates(List<Date> dates);

    @MapMapping(keyDateFormat = "qwertz")
    Map<String, Integer> fromDateKeys(Map<Date, Long> dates);

    @MapMapping(valueDateFormat = "qwertz")
    Map<Long, String> fromDateValues(Map<String, Date> dates);
}
