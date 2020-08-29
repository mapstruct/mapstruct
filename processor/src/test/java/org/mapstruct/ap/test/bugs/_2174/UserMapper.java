/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2174;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(target = "address.city", source = "city")
    @Mapping(target = "address.code", source = "postalCode")
    User map(UserDto dto) throws CityNotFoundException, PostalCodeNotFoundException;

    default City mapCity(String city) throws CityNotFoundException {
        if ( "Test City".equals( city ) ) {
            return new City( city );
        }

        throw new CityNotFoundException( "City with name '" + city + "' does not exist" );
    }

    default PostalCode mapCode(String code) throws PostalCodeNotFoundException {
        if ( "10000".equals( code ) ) {
            return new PostalCode( code );
        }

        throw new PostalCodeNotFoundException( "Postal code '" + code + "' does not exist" );
    }

    class UserDto {
        private final String city;
        private final String postalCode;

        public UserDto(String city, String postalCode) {
            this.city = city;
            this.postalCode = postalCode;
        }

        public String getCity() {
            return city;
        }

        public String getPostalCode() {
            return postalCode;
        }
    }

    class User {

        private final Address address;

        public User(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }
    }

    class Address {
        private final City city;
        private final PostalCode code;

        public Address(City city, PostalCode code) {
            this.city = city;
            this.code = code;
        }

        public City getCity() {
            return city;
        }

        public PostalCode getCode() {
            return code;
        }
    }

    class City {

        private final String name;

        public City(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class PostalCode {

        private final String code;

        public PostalCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    class CityNotFoundException extends Exception {

        public CityNotFoundException(String message) {
            super( message );
        }
    }

    class PostalCodeNotFoundException extends Exception {

        public PostalCodeNotFoundException(String message) {
            super( message );
        }
    }
}
