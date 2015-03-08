/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.conversion.jodatime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm z";

    String LOCAL_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";

    String LOCAL_DATE_FORMAT = "dd.MM.yyyy";

    String LOCAL_TIME_FORMAT = "HH:mm";

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings({
        @Mapping(target = "dateTime", dateFormat = DATE_TIME_FORMAT),
        @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT),
        @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT),
        @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    })
    Target sourceToTarget(Source source);

    Target sourceToTargetDefaultMapping(Source source);

    @Mapping(target = "dateTime", dateFormat = DATE_TIME_FORMAT)
    Target sourceToTargetDateTimeMapped(Source source);

    @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT)
    Target sourceToTargetLocalDateTimeMapped(Source source);

    @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT)
    Target sourceToTargetLocalDateMapped(Source source);

    @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    Target sourceToTargetLocalTimeMapped(Source source);

    @Mappings({
        @Mapping(target = "dateTime", dateFormat = DATE_TIME_FORMAT),
        @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT),
        @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT),
        @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    })
    Source targetToSource(Target target);

    @Mapping(target = "dateTime", dateFormat = DATE_TIME_FORMAT)
    Source targetToSourceDateTimeMapped(Target target);

    @Mapping(target = "localDateTime", dateFormat = LOCAL_DATE_TIME_FORMAT)
    Source targetToSourceLocalDateTimeMapped(Target target);

    @Mapping(target = "localDate", dateFormat = LOCAL_DATE_FORMAT)
    Source targetToSourceLocalDateMapped(Target target);

    @Mapping(target = "localTime", dateFormat = LOCAL_TIME_FORMAT)
    Source targetToSourceLocalTimeMapped(Target target);

    Source targetToSourceDefaultMapping(Target target);
}
