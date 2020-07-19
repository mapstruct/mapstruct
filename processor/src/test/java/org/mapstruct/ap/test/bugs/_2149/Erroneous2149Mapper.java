/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2149;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Erroneous2149Mapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = ".", source = "name")
    Target map(Source source);

    class Target {

        private String firstName;
        private String age;
        private String address;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    class Source {

        private final String age;
        private final Name name;

        public Source(String age, Name name) {
            this.age = age;
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public Name getName() {
            return name;
        }
    }

    class Name {

        private final String firstName;

        public Name(String firstName) {
            this.firstName = firstName;
        }

        public String getFirstName() {
            return firstName;
        }
    }
}
