/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(mappingControl = NoConversion.class)
public interface ErroneousBuiltInAndMethodMapper {

    Target map(Source source);

    default ZonedDateTime fromInt(int time) {
        return ZonedDateTime.ofInstant( Instant.ofEpochMilli( time ), ZoneOffset.UTC );
    }

    class Source {
        private final int time;

        public Source(int time) {
            this.time = time;
        }

        public int getTime() {
            return time;
        }
    }

    class Target {
        private Calendar time;

        public Target(Calendar time) {
            this.time = time;
        }

        public Calendar getTime() {
            return time;
        }
    }
}
