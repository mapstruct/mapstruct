/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.visibility;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SimpleWithPublicConstructorMapper {

    SimpleWithPublicConstructorMapper INSTANCE = Mappers.getMapper( SimpleWithPublicConstructorMapper.class );

    Person map(PersonDto dto);

    class Person {

        private final String name;
        private final int age;

        protected Person() {
            this( "From Constructor", -1 );
        }

        protected Person(String name) {
            this( name, -1 );
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
