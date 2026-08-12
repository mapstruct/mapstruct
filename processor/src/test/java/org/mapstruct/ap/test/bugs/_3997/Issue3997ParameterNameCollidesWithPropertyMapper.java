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
 * Parameter name collides with a source property, but the rest of the path is not on that property.
 * Forward mapping strips the parameter name; inverse must strip too so ignore on {@code client} works
 * for other nested targets, and {@code id} is still mapped.
 *
 * @author Kamil Krzywanski
 */
@Mapper
public interface Issue3997ParameterNameCollidesWithPropertyMapper {

    Issue3997ParameterNameCollidesWithPropertyMapper INSTANCE =
        Mappers.getMapper( Issue3997ParameterNameCollidesWithPropertyMapper.class );

    // parameter name "client" also exists as Order.getClient(), but Client has no id —
    // SourceReference strips and maps Order.getId() → dtoId
    @Mapping(target = "dtoId", source = "client.id")
    OrderDto toDto(Order client);

    @InheritInverseConfiguration
    @Mapping(target = "client", ignore = true)
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
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
