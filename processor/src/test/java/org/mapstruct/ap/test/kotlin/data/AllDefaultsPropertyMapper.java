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
public interface AllDefaultsPropertyMapper {

    AllDefaultsPropertyMapper INSTANCE = Mappers.getMapper( AllDefaultsPropertyMapper.class );

    AllDefaultsProperty map(Source source);

    class Source {

        private final String firstName;
        private final String lastName;

        public Source(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}
