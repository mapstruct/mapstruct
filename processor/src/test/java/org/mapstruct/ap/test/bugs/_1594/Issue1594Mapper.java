/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1594;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1594Mapper {

    Issue1594Mapper INSTANCE = Mappers.getMapper( Issue1594Mapper.class );

    @Mappings({
        @Mapping(target = "address.country.oid", expression = "java(dto.getFullAddress().split( \"-\" )[0])"),
        @Mapping(target = "address.city.oid", expression = "java(dto.getFullAddress().split( \"-\" )[1])"),
    })
    Client toClient(Dto dto);

    class Client {
        private Address address;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    class Address {

        private Id country;
        private Id city;

        public Id getCountry() {
            return country;
        }

        public void setCountry(Id country) {
            this.country = country;
        }

        public Id getCity() {
            return city;
        }

        public void setCity(Id city) {
            this.city = city;
        }
    }

    class Id {
        private String oid;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }
    }

    class Dto {
        private String fullAddress;

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }
    }
}
