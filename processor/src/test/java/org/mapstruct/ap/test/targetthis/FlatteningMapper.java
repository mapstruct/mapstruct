/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface FlatteningMapper {

    FlatteningMapper INSTANCE = Mappers.getMapper( FlatteningMapper.class );

    @Mapping(target = ".", source = "name")
    @Mapping(target = ".", source = "account")
    Customer map(CustomerDTO customer);

    class Customer {

        private String name;
        private String id;
        private String details;
        private String number;

        public String getName() {
            return name;
        }

        public Customer setName(String name) {
            this.name = name;
            return this;
        }

        public String getId() {
            return id;
        }

        public Customer setId(String id) {
            this.id = id;
            return this;
        }

        public String getDetails() {
            return details;
        }

        public Customer setDetails(String details) {
            this.details = details;
            return this;
        }

        public String getNumber() {
            return number;
        }

        public Customer setNumber(String number) {
            this.number = number;
            return this;
        }
    }

    class CustomerDTO {

        private NameDTO name;
        private AccountDTO account;

        public NameDTO getName() {
            return name;
        }

        public CustomerDTO setName(NameDTO name) {
            this.name = name;
            return this;
        }

        public AccountDTO getAccount() {
            return account;
        }

        public CustomerDTO setAccount(AccountDTO account) {
            this.account = account;
            return this;
        }
    }

    class NameDTO {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public NameDTO setName(String name) {
            this.name = name;
            return this;
        }

        public String getId() {
            return id;
        }

        public NameDTO setId(String id) {
            this.id = id;
            return this;
        }
    }

    class AccountDTO {
        private String number;
        private String details;

        public String getNumber() {
            return number;
        }

        public AccountDTO setNumber(String number) {
            this.number = number;
            return this;
        }

        public String getDetails() {
            return details;
        }

        public AccountDTO setDetails(String details) {
            this.details = details;
            return this;
        }
    }

}
