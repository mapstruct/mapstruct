/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2921;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2921Mapper {

    Issue2921Mapper INSTANCE = Mappers.getMapper( Issue2921Mapper.class );

    Target map(Source source);

    default Short toShort(Integer value) {
        throw new UnsupportedOperationException( "toShort method should not be used" );
    }

    class Source {
        private final Integer value;

        public Source(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    class Target {
        private final int value;

        public Target(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
