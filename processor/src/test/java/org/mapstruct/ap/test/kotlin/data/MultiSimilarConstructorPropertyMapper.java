/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface MultiSimilarConstructorPropertyMapper {

    MultiSimilarConstructorPropertyMapper INSTANCE = Mappers.getMapper( MultiSimilarConstructorPropertyMapper.class );

    PrimaryString map(Source source, String displayName);

    PrimaryInt map(Source source, int age);

    PrimaryLong map(Source source, long age);

    PrimaryBoolean map(Source source, boolean active);

    PrimaryByte map(Source source, byte b);

    PrimaryShort map(Source source, short age);

    PrimaryChar map(Source source, char c);

    PrimaryFloat map(Source source, float price);

    PrimaryDouble map(Source source, double price);

    PrimaryArray map(Source source, String[] elements);

    class Source {

        private final String firstName;
        private final String lastName;

        public Source(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}
