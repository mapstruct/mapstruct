/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.simple;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OptionalToTypeWithMappingMapper {

    OptionalToTypeWithMappingMapper INSTANCE = Mappers.getMapper( OptionalToTypeWithMappingMapper.class );

    @Mapping(target = "name", source = "source.firstName")
    @Mapping(target = "value", source = "lastName")
    Target map(Optional<Source> source);

    class Target {

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

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
