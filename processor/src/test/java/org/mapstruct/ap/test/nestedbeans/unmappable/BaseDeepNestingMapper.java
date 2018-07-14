/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public abstract class BaseDeepNestingMapper {

    public abstract UserDto userToUserDto(User user);

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
