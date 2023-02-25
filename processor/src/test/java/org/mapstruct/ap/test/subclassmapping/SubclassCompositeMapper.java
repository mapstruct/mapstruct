/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleCollection;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleCollectionDto;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubclassCompositeMapper {
    SubclassCompositeMapper INSTANCE = Mappers.getMapper( SubclassCompositeMapper.class );

    VehicleCollectionDto map(VehicleCollection vehicles);

    @CompositeSubclassMappingAnnotation
    VehicleDto map(Vehicle vehicle);

    VehicleCollection mapInverse(VehicleCollectionDto vehicles);

    @InheritInverseConfiguration
    Vehicle mapInverse(VehicleDto dto);
}
