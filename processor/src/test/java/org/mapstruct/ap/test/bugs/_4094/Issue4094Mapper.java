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
 * Reproducer for #4094: a nested path ignore under a property that is also mapped as a whole
 * must not force an empty forged method when {@code Mapper#uses} provides a suitable method and
 * the parent uses {@code @BeanMapping(ignoreByDefault = true)}.
 */
@Mapper(uses = Issue4094PersonMapper.class)
public interface Issue4094Mapper {

    Issue4094Mapper INSTANCE = Mappers.getMapper( Issue4094Mapper.class );

    /**
     * Nested ignore of {@code relatedPerson.relatedInformation} is redundant with
     * {@link Issue4094PersonMapper} (which already uses ignoreByDefault), but must not prevent
     * delegation to that mapper.
     */
    @Mapping(target = "type", source = "type")
    @Mapping(target = "relatedPerson.relatedInformation", ignore = true)
    @Mapping(target = "relatedPerson", source = "relatedPerson")
    @BeanMapping(ignoreByDefault = true)
    PersonInformationDto toDto(PersonInformation personInformation);

    /**
     * Same shape without the nested ignore — baseline that already worked before the fix.
     */
    @Mapping(target = "type", source = "type")
    @Mapping(target = "relatedPerson", source = "relatedPerson")
    @BeanMapping(ignoreByDefault = true)
    PersonInformationDto toDtoWithoutNestedIgnore(PersonInformation personInformation);

    class PersonInformation {
        private String type;
        private Person relatedPerson;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Person getRelatedPerson() {
            return relatedPerson;
        }

        public void setRelatedPerson(Person relatedPerson) {
            this.relatedPerson = relatedPerson;
        }
    }

    class Person {
        private String id;
        private String name;
        private String gender;
        private String relatedInformation;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getRelatedInformation() {
            return relatedInformation;
        }

        public void setRelatedInformation(String relatedInformation) {
            this.relatedInformation = relatedInformation;
        }
    }

    class PersonInformationDto {
        private String type;
        private PersonDto relatedPerson;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public PersonDto getRelatedPerson() {
            return relatedPerson;
        }

        public void setRelatedPerson(PersonDto relatedPerson) {
            this.relatedPerson = relatedPerson;
        }
    }

    class PersonDto {
        private String id;
        private String name;
        private String genderCode;
        private String relatedInformation;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGenderCode() {
            return genderCode;
        }

        public void setGenderCode(String genderCode) {
            this.genderCode = genderCode;
        }

        public String getRelatedInformation() {
            return relatedInformation;
        }

        public void setRelatedInformation(String relatedInformation) {
            this.relatedInformation = relatedInformation;
        }
    }
}
