/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

    @Mappings( {
        @Mapping(target = "date", dateFormat = "qwertz"),
        @Mapping(target = "zonedDateTime", dateFormat = "qwertz"),
        @Mapping(target = "localDateTime", dateFormat = "qwertz"),
        @Mapping(target = "localDate", dateFormat = "qwertz"),
        @Mapping(target = "localTime", dateFormat = "qwertz"),
        @Mapping(target = "dateTime", dateFormat = "qwertz"),
        @Mapping(target = "jodaLocalDateTime", dateFormat = "qwertz"),
        @Mapping(target = "jodaLocalDate", dateFormat = "qwertz"),
        @Mapping(target = "jodaLocalTime", dateFormat = "qwertz")
    } )
    Target sourceToTarget(Source source);

    @IterableMapping(dateFormat = "qwertz")
    List<String> fromDates(List<Date> dates);

    @MapMapping(keyDateFormat = "qwertz")
    Map<String, Integer> fromDateKeys(Map<Date, Long> dates);

    @MapMapping(valueDateFormat = "qwertz")
    Map<Long, String> fromDateValues(Map<String, Date> dates);
}
