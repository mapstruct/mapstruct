/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapToBeanFromMapWithSourceDeepNested {

    MapToBeanFromMapWithSourceDeepNested INSTANCE = Mappers.getMapper( MapToBeanFromMapWithSourceDeepNested.class );

    @Mapping(target = "targetName", source = "innerMap.john\\.doe.person.firstName")
    Target toTarget(Source source);

    @Mapping(target = "targetName", source = "source.innerMap.john\\.doe.person.firstName")
    Target toTargetWithLeadingParameterName(Source source);

    class Source {

        private Map<String, SourceEntry> innerMap;

        public Map<String, SourceEntry> getInnerMap() {
            return innerMap;
        }

        public void setInnerMap(
            Map<String, SourceEntry> innerMap) {
            this.innerMap = innerMap;
        }
    }

    class SourceEntry {

        private Person person;

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

    }

    class Person {

        private String firstName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }

    class Target {

        String targetName;

        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }
    }

}
