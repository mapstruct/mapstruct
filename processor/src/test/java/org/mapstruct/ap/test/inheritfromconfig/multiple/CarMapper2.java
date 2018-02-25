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
package org.mapstruct.ap.test.inheritfromconfig.multiple;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CarMapper2 {
    public static final CarMapper2 MAPPER = Mappers.getMapper( CarMapper2.class );

    @Mappings({
        @Mapping(target = "maker", source = "manufacturer"),
        @Mapping(target = "seatCount", source = "numberOfSeats")
    })
    public abstract Car2Dto mapToBase(Car2Entity car);

    @Mappings({
        @Mapping(target = "manufacturer", constant = "ford"),
        @Mapping(target = "numberOfSeats", ignore = true)
    })
    public abstract Car2Entity mapFromBase(Car2Dto carDto);

    @InheritConfiguration(name = "mapToBase")
    @InheritInverseConfiguration(name = "mapFromBase")
    public abstract Car2Dto mapTo(Car2Entity car);

    @InheritConfiguration(name = "mapFromBase")
    @InheritInverseConfiguration(name = "mapToBase")
    public abstract Car2Entity mapFrom(Car2Dto carDto);

}
