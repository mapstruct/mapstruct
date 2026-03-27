/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig.multiple;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = EntityToDtoMappingConfig.class)
public abstract class CarMapper {

    public static final CarMapper MAPPER = Mappers.getMapper( CarMapper.class );

    @Mappings({
        @Mapping(target = "maker", source = "manufacturer"),
        @Mapping(target = "seatCount", source = "numberOfSeats")
    })
    // additional mapping inherited from EntityToDtoMappingConfig.entityToDto method :
    //  @Mapping(target = "dbId", source = "id")
    //  @Mapping(target = "links", ignore = true)
    @InheritConfiguration(name = "entityToDto")
    public abstract CarDto mapTo(CarEntity car);

    @InheritInverseConfiguration(name = "mapTo")
    @InheritConfiguration(name = "dtoToEntity")
    // @inheritInverseConfiguration should map both maker and seatCount properties.
    // additional mapping should also be inherited from EntityToDtoMappingConfig.dtoToEntity method,
    //    @Mapping(target = "id", source = "dbId")
    //    @Mapping(target = "createdBy", ignore = true)
    //    @Mapping(target = "creationDate", ignore = true)
    //    @Mapping(target = "lastModifiedBy", constant = "restApiUser")
    //    @Mapping(target = "lastModifiedDate", ignore = true)
    public abstract CarEntity mapFrom(CarDto carDto);

}
