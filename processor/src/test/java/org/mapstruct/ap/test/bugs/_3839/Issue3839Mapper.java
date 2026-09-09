/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3839;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * A single {@code target = "."} mapping whose source contributes both target properties.
 */
@Mapper( unmappedSourcePolicy = ReportingPolicy.ERROR )
public interface Issue3839Mapper {

    @Mapping( target = ".", source = "record" )
    Customer map(CustomerDto customerDto);

    class CustomerDto {

        private Record record;

        public Record getRecord() {
            return record;
        }

        public void setRecord(Record record) {
            this.record = record;
        }
    }

    class Record {

        private String name;

        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    class Customer {

        private String name;

        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
