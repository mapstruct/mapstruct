/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.OffsetDateTime;
import java.time.OffsetTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OffsetMapper {
    TextValues toText(TimeValues source);

    TimeValues fromText(TextValues source);

    @Mapping(target = "dateTime", dateFormat = "dd/MM/uuuu HH:mm:ss xx")
    @Mapping(target = "time", dateFormat = "HH.mm.ss xx")
    TextValues toFormattedText(TimeValues source);

    @Mapping(target = "dateTime", dateFormat = "dd/MM/uuuu HH:mm:ss xx")
    @Mapping(target = "time", dateFormat = "HH.mm.ss xx")
    TimeValues fromFormattedText(TextValues source);

    class TimeValues {
        public OffsetDateTime dateTime;
        public OffsetTime time;
    }

    class TextValues {
        public String dateTime;
        public String time;
    }
}
