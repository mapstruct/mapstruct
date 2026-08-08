/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4094;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Same-type nested property with a nested ignore: must forge (not direct-assign)
 * so the ignore is applied under {@code ignoreByDefault}.
 */
@Mapper
public interface Issue4094SameTypeMapper {

    Issue4094SameTypeMapper INSTANCE = Mappers.getMapper( Issue4094SameTypeMapper.class );

    @Mapping(target = "relatedPerson", source = "relatedPerson")
    @Mapping(target = "relatedPerson.relatedInformation", ignore = true)
    @BeanMapping(ignoreByDefault = true)
    Holder toDto(Holder source);

    class Holder {
        private Person relatedPerson;

        public Person getRelatedPerson() {
            return relatedPerson;
        }

        public void setRelatedPerson(Person relatedPerson) {
            this.relatedPerson = relatedPerson;
        }
    }

    class Person {
        private String name;
        private String relatedInformation;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelatedInformation() {
            return relatedInformation;
        }

        public void setRelatedInformation(String relatedInformation) {
            this.relatedInformation = relatedInformation;
        }
    }
}
