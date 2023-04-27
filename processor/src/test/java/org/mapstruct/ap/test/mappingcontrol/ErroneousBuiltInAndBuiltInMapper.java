/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import java.time.ZonedDateTime;
import java.util.Date;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(mappingControl = NoConversion.class)
public interface ErroneousBuiltInAndBuiltInMapper {

    Target map(Source source);

    class Source {
        private final ZonedDateTime time;

        public Source(ZonedDateTime time) {
            this.time = time;
        }

        public ZonedDateTime getTime() {
            return time;
        }
    }

    class Target {
        private final Date time;

        public Target(Date time) {
            this.time = time;
        }

        public Date getTime() {
            return time;
        }
    }
}
