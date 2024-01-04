/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import java.time.Instant;
import java.util.Date;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(mappingControl = NoConversion.class)
public interface ErroneousMethodAndConversionMapper {

    Target map(Source source);

    default long fromInstant(Instant value) {
        return value != null ? value.getEpochSecond() : null;
    }

    class Source {
        private final Date time;

        public Source(Date time) {
            this.time = time;
        }

        public Date getTime() {
            return time;
        }
    }

    class Target {
        private long time;

        public Target(long time) {
            this.time = time;
        }

        public long getTime() {
            return time;
        }
    }
}
