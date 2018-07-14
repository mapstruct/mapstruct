/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public abstract class BaseDeepListMapper {

    public abstract UserDto userToUserDto(User user);

    public HouseDto map(House house) {
        return new HouseDto();
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
