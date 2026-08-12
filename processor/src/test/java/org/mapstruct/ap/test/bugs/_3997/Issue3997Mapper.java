/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3997;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Kamil Krzywanski
 */
@Mapper
public interface Issue3997Mapper {

    Issue3997Mapper INSTANCE = Mappers.getMapper( Issue3997Mapper.class );

    @Mapping(target = "clientId", source = "source.client.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "city", source = "source.address.city")
    OrderDto toDto(Order source);

    @InheritInverseConfiguration
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "address", ignore = true)
    Order toEntity(OrderDto dto);

    class Order {
        private Long id;
        private Client client;
        private String name;
        private Address address;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    class OrderDto {
        private Long id;
        private Long clientId;
        private String name;
        private String city;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getClientId() {
            return clientId;
        }

        public void setClientId(Long clientId) {
            this.clientId = clientId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    class Client {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    class Address {
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
