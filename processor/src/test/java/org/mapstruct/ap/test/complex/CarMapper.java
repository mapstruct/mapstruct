/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.complex;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.complex._target.CarDto;
import org.mapstruct.ap.test.complex._target.PersonDto;
import org.mapstruct.ap.test.complex.other.DateMapper;
import org.mapstruct.ap.test.complex.source.Car;
import org.mapstruct.ap.test.complex.source.Person;
import org.mapstruct.factory.Mappers;

@Mapper(uses = DateMapper.class)
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mappings({
        @Mapping(target = "seatCount", source = "numberOfSeats"),
        @Mapping(target = "manufacturingYear", source = "manufacturingDate")
    })
    CarDto carToCarDto(Car car);

    @InheritInverseConfiguration
    Car carDtoToCar(CarDto carDto);

    List<CarDto> carsToCarDtos(List<Car> cars);

    @InheritInverseConfiguration
    List<Car> carDtosToCars(List<CarDto> carDtos);

    PersonDto personToPersonDto(Person person);

    @InheritInverseConfiguration
    Person personDtoToPerson(PersonDto personDto);

    List<PersonDto> personsToPersonDtos(List<Person> persons);

    @InheritInverseConfiguration
    List<Person> personDtosToPersons(List<PersonDto> personDtos);
}
