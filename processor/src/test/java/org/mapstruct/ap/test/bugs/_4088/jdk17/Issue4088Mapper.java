/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4088.jdk17;

import org.jspecify.annotations.Nullable;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue4088Mapper {

    Issue4088Mapper INSTANCE = Mappers.getMapper( Issue4088Mapper.class );

    @Mapping(target = "date", source = "appointment.dateTime")
    Target create(Request request);

    @InheritConfiguration
    void update(Request request, @MappingTarget Target target);

    record Request(Appointment appointment) {

        record Appointment(SourceDateTime dateTime) {
        }
    }

    class Target {

        private @Nullable TargetDate date;

        public TargetDate getDate() {
            return date;
        }

        public void setDate(@Nullable TargetDate date) {
            this.date = date;
        }
    }

    class TargetDate {

        private int day;
        private int month;
        private int year;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    class SourceDateTime {

        private final int day;
        private final int month;
        private final int year;

        SourceDateTime(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }
    }
}
