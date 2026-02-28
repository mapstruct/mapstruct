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
public interface MultiConstructorPropertyMapper {

    MultiConstructorPropertyMapper INSTANCE = Mappers.getMapper( MultiConstructorPropertyMapper.class );

    MultiConstructorProperty map(Source source);

    class Source {

        private final String firstName;
        private final String lastName;
        private final String displayName;

        public Source(String firstName, String lastName, String displayName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.displayName = displayName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
