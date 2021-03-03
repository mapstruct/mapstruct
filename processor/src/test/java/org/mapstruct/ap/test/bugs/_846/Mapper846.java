/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._846;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public class Mapper846 {

    interface BInterface {

        String getName();

        void setName(String s);
    }

    static class B implements BInterface {

        String name;

        B() {
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }
    }

    static class A {

        String aName;

        A() {
        }

        public String getaName() {
            return aName;
        }

        public void setaName(String name) {
            this.aName = name;
        }
    }

    @Mapper
    interface MyMapper {

        @Mapping(target = "aName", source = "name")
        A convert(BInterface b);

        @InheritInverseConfiguration
        void convert(@MappingTarget BInterface target, A source);
    }
}
