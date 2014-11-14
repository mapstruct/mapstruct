/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.mapnulltodefault;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.MapNullToDefault;
import org.mapstruct.MapNullToDefaultStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.mapnulltodefault.source.Car;
import org.mapstruct.ap.test.mapnulltodefault.target.CarDto;
import org.mapstruct.factory.Mappers;

@Mapper(imports = UUID.class, mapNullToDefaultStrategy = MapNullToDefaultStrategy.MAP_NULL_TO_DEFAULT )
public interface CarMapperSettingOnMapper {

    CarMapperSettingOnMapper INSTANCE = Mappers.getMapper( CarMapperSettingOnMapper.class );

    @Mappings({
        @Mapping(target = "seatCount", source = "numberOfSeats"),
        @Mapping(target = "model", constant = "ModelT"),
        @Mapping(target = "catalogId", expression = "java( UUID.randomUUID().toString() )")
    })
    CarDto carToCarDto(Car car);


    @MapNullToDefault(MapNullToDefaultStrategy.DEFAULT)
    List<CarDto> carsToCarDtos(List<Car> cars);


    @MapNullToDefault(MapNullToDefaultStrategy.MAP_NULL_TO_NULL)
    Map<Integer, CarDto> carsToCarDtoMap(Map<Integer, Car> cars);
}
