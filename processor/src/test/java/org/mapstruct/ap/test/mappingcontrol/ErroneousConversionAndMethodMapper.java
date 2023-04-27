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
public interface ErroneousConversionAndMethodMapper {

    Target map(Source source);

    default Instant fromDate(int time) {
        return Instant.ofEpochMilli( time );
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
        private Date time;

        public Target(Date time) {
            this.time = time;
        }

        public Date getTime() {
            return time;
        }
    }
}
