/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface UnsignedPropertyMapper {

    UnsignedPropertyMapper INSTANCE = Mappers.getMapper( UnsignedPropertyMapper.class );

    UnsignedProperty map(Source source);

    Source map(UnsignedProperty property);

    class Source {

        private final int age;

        public Source(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }
}
