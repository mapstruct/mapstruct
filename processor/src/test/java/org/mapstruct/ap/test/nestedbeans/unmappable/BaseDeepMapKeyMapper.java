/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public abstract class BaseDeepMapKeyMapper {

    public abstract UserDto userToUserDto(User user);

    public CarDto map(Car carDto) {
        return new CarDto();
    }

    public HouseDto map(House house) {
        return new HouseDto();
    }

    public ForeignWordDto map(ForeignWord word) {
        return new ForeignWordDto();
    }

    public ComputerDto map(Computer computer) {
        return new ComputerDto();
    }

    public CatDto map(Cat cat) {
        return new CatDto();
    }

}
