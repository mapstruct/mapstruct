/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ap.test.bugs._2795.dto.CarDto;
import org.mapstruct.ap.test.bugs._2795.model.Car;

@Mapper( uses = { MapperConfig.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public abstract class CarMapper {

    public abstract CarDto carToCarDto(Car car);

    public abstract Car carDtoToCar(CarDto carDto);

    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    @Mapping( target = "id", ignore = true )
    public abstract Car updateCarWithCarDto(CarDto update, @MappingTarget Car destination);

}
