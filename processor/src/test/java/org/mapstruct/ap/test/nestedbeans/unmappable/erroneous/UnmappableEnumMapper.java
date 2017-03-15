/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
public abstract class UnmappableEnumMapper {

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
