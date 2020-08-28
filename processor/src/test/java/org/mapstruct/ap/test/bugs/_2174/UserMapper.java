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
    User map(UserDto dto) throws CityNotFoundException;

    default City mapCity(String city) throws CityNotFoundException {
        if ( "Test City".equals( city ) ) {
            return new City( city );
        }

        throw new CityNotFoundException( "City with name '" + city + "' does not exist" );
    }

    class UserDto {
        private final String city;

        public UserDto(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
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

        public Address(City city) {
            this.city = city;
        }

        public City getCity() {
            return city;
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

    class CityNotFoundException extends Exception {

        public CityNotFoundException(String message) {
            super( message );
        }
    }
}
