/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public abstract class BaseCollectionElementPropertyMapper {

    public abstract UserDto userToUserDto(User user);

    public HouseDto map(House house) {
        return new HouseDto();
    }

    public CarDto map(Car carDto) {
        return new CarDto();
    }

    public DictionaryDto map(Dictionary dictionary) {
        return new DictionaryDto();
    }

    public CatDto map(Cat cat) {
        return new CatDto();
    }
}
