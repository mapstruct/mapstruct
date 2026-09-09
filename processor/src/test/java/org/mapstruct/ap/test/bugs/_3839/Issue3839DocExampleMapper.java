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
 * The "target this" example from the reference documentation.
 */
@Mapper( unmappedSourcePolicy = ReportingPolicy.ERROR )
public interface Issue3839DocExampleMapper {

    @Mapping( target = "name", source = "record.name" )
    @Mapping( target = ".", source = "record" )
    @Mapping( target = ".", source = "account" )
    Customer map(CustomerDto customerDto);

    class CustomerDto {

        private Record record;

        private Account account;

        public Record getRecord() {
            return record;
        }

        public void setRecord(Record record) {
            this.record = record;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
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

    class Account {

        private String name;

        private long number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getNumber() {
            return number;
        }

        public void setNumber(long number) {
            this.number = number;
        }
    }

    class Customer {

        private String name;

        private String description;

        private long number;

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

        public long getNumber() {
            return number;
        }

        public void setNumber(long number) {
            this.number = number;
        }
    }
}
