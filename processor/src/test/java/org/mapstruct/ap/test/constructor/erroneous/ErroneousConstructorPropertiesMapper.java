/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.erroneous;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.ap.test.constructor.ConstructorProperties;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousConstructorPropertiesMapper {

    PersonWithIncorrectConstructorProperties map(PersonDto dto);

    class PersonWithIncorrectConstructorProperties {

        private final String name;
        private final int age;

        @ConstructorProperties({ "name" })
        public PersonWithIncorrectConstructorProperties(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
