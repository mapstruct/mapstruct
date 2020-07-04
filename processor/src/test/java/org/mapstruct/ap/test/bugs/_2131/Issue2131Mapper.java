/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2131;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface Issue2131Mapper {

    Issue2131Mapper INSTANCE = Mappers.getMapper( Issue2131Mapper.class );

    TestDto map(TestModel source);

    class TestModel {
        private final String name;

        public TestModel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class TestDto {
        private String name;

        public TestDto(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
