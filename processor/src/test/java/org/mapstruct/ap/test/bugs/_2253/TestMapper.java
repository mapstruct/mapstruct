/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2253;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper( TestMapper.class );

    Person map(PersonDto personDto, String name);

    class Person {
        private final int age;
        private final String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

    class PersonDto {
        private final int age;

        public PersonDto(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }
}
