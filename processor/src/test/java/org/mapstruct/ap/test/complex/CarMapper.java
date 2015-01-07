/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.complex;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.InheritInverseConfiguration;
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
        @Mapping(source = "numberOfSeats", target = "seatCount"),
        @Mapping(source = "manufacturingDate", target = "manufacturingYear")
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
