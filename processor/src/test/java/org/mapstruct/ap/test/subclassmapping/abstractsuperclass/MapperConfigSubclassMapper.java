/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfigSubclassMapper.Config.class)
public interface MapperConfigSubclassMapper {

    MapperConfigSubclassMapper INSTANCE = Mappers.getMapper( MapperConfigSubclassMapper.class );

    @MapperConfig(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
        subclassExhaustiveException = CustomSubclassMappingException.class)
    interface Config {
    }

    @SubclassMapping(source = Car.class, target = CarDto.class)
    @SubclassMapping(source = Bike.class, target = BikeDto.class)
    VehicleDto map(AbstractVehicle vehicle);

    VehicleCollectionDto mapInverse(VehicleCollection vehicles);
}
