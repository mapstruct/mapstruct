/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3317;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3317Mapper {

    Issue3317Mapper INSTANCE = Mappers.getMapper( Issue3317Mapper.class );

    Target map(int id, long value);

    class Target {

        private final int id;
        private final long value;

        public Target(int id, long value) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public long getValue() {
            return value;
        }
    }
}
