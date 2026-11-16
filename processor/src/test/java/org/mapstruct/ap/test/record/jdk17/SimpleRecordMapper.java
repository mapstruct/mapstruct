/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.record.jdk17;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SimpleRecordMapper {

    SimpleRecordMapper INSTANCE = Mappers.getMapper( SimpleRecordMapper.class );

    @Mapping(target = "mail", source = "email")
    CustomerEntity fromRecord(CustomerDto record);

    @InheritInverseConfiguration
    CustomerDto toRecord(CustomerEntity entity);

    @Mapping(target = "value", source = "name")
    GenericRecord<String> toValue(CustomerEntity entity);

    record CustomerDto(String name, String email) {

    }

    record GenericRecord<T>(T value) {

    }

    class CustomerEntity {

        private String name;
        private String mail;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }
    }
}
