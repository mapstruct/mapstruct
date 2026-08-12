/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3997;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Parameter name collides with a property and the full path resolves as properties
 * ({@code Order.getClient().getId()}). Forward mapping keeps the property path; inverse
 * must keep {@code client.id} as well (not strip to {@code id}).
 *
 * @author Kamil Krzywanski
 */
@Mapper
public interface Issue3997ParameterNameIsPropertyPathMapper {

    Issue3997ParameterNameIsPropertyPathMapper INSTANCE =
        Mappers.getMapper( Issue3997ParameterNameIsPropertyPathMapper.class );

    @BeanMapping(ignoreUnmappedSourceProperties = "id")
    @Mapping(target = "dtoId", source = "client.id")
    OrderDto toDto(Order client);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    Order toEntity(OrderDto dto);

    class Order {
        private Long id;
        private Client client;

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
    }

    class OrderDto {
        private Long dtoId;

        public Long getDtoId() {
            return dtoId;
        }

        public void setDtoId(Long dtoId) {
            this.dtoId = dtoId;
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
}
