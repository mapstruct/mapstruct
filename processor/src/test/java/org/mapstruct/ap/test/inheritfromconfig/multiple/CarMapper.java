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

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = EntityToDtoMappingConfig.class)
public abstract class CarMapper {

    public static final CarMapper MAPPER = Mappers.getMapper( CarMapper.class );

    @Mappings({
        @Mapping(target = "maker", source = "manufacturer"),
        @Mapping(target = "seatCount", source = "numberOfSeats")
    })
    // additional mapping inherited from EntityToDtoMappingConfig.entityToDto method :
    //  @Mapping(target = "dbId", source = "id")
    //  @Mapping(target = "links", ignore = true)
    @InheritConfiguration(name = "entityToDto")
    public abstract CarDto mapTo(CarEntity car);

    @InheritInverseConfiguration(name = "mapTo")
    @InheritConfiguration(name = "dtoToEntity")
    // @inheritInverseConfiguration should map both maker and seatCount properties.
    // additional mapping should also be inherited from EntityToDtoMappingConfig.dtoToEntity method,
    //    @Mapping(target = "id", source = "dbId")
    //    @Mapping(target = "createdBy", ignore = true)
    //    @Mapping(target = "creationDate", ignore = true)
    //    @Mapping(target = "lastModifiedBy", constant = "restApiUser")
    //    @Mapping(target = "lastModifiedDate", ignore = true)
    public abstract CarEntity mapFrom(CarDto carDto);

}
