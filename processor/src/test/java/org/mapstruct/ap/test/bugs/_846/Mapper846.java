/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

        @Mapping(source = "name", target = "aName")
        A convert(BInterface b);

        @InheritInverseConfiguration
        void convert(@MappingTarget BInterface target, A source);
    }
}
