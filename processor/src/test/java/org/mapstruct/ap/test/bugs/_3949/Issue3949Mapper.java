/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3949;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface Issue3949Mapper {

    Issue3949Mapper INSTANCE = Mappers.getMapper( Issue3949Mapper.class );

    void overwriteDate(@MappingTarget TargetDate target, DateSource dateSource);

    void overwriteString(@MappingTarget TargetString target, StringSource stringSource);

    void overwriteDateWithConversion(@MappingTarget TargetDate target, StringSource dateSource);

    void overwriteStringWithConversion(@MappingTarget TargetString target, DateSource stringSource);

    void updateParent(@MappingTarget ParentTarget target, ParentSource source);

    class DateSource {
        public LocalDate getDate() {
            return null;
        }
    }

    class StringSource {
        public String getDate() {
            return null;
        }
    }

    class ParentSource {
        public ParentSource getChild() {
            return null;
        }
    }

    class TargetDate {
        private LocalDate date = LocalDate.now();
        private String string = "";

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public void setDate(String date) {
            this.string = date;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getString() {
            return string;
        }
    }

    class TargetString {
        private LocalDate date = LocalDate.now();
        private String string = "";

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public void setDate(String date) {
            this.string = date;
        }

        public String getDate() {
            return string;
        }

        public LocalDate getDateValue() {
            return date;
        }

    }

    class ParentTarget {
        private ParentTarget child;

        public ParentTarget getChild() {
            return child;
        }

        public void setChild(String child) {
            throw new IllegalArgumentException();
        }

        public void setChild(ParentTarget child) {
            this.child = child;
        }
    }
}
