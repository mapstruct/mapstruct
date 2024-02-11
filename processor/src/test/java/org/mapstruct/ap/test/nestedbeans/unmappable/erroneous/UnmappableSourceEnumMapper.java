/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable.erroneous;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.Car;
import org.mapstruct.ap.test.nestedbeans.unmappable.CarDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Cat;
import org.mapstruct.ap.test.nestedbeans.unmappable.CatDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Color;
import org.mapstruct.ap.test.nestedbeans.unmappable.ColorDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Computer;
import org.mapstruct.ap.test.nestedbeans.unmappable.ComputerDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Dictionary;
import org.mapstruct.ap.test.nestedbeans.unmappable.DictionaryDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.User;
import org.mapstruct.ap.test.nestedbeans.unmappable.UserDto;

@Mapper
public abstract class UnmappableSourceEnumMapper {

    abstract UserDto userToUserDto(User user);

    public ColorDto map(Color color) {
        return new ColorDto();
    }

    public CarDto map(Car carDto) {
        return new CarDto();
    }

    public DictionaryDto map(Dictionary dictionary) {
        return new DictionaryDto();
    }

    public ComputerDto map(Computer computer) {
        return new ComputerDto();
    }

    public CatDto map(Cat cat) {
        return new CatDto();
    }
}
