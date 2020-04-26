/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.erroneous;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.constructor.PersonDto;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousAmbiguousConstructorsMapper {

    PersonWithMultipleConstructors map(PersonDto dto);

    class PersonWithMultipleConstructors {

        private final String name;
        private final int age;

        public PersonWithMultipleConstructors(String name) {
            this( name, -1 );
        }

        public PersonWithMultipleConstructors(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
