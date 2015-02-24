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
package org.mapstruct.ap.test.inheritfromconfig;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(
    config = AutoInheritedConfig.class
)
public interface Erroneous2Mapper {
    Erroneous2Mapper INSTANCE = Mappers.getMapper( Erroneous2Mapper.class );

    @InheritConfiguration(name = "toCarEntity2")
    CarEntity toCarEntity1(CarDto carDto);

    @InheritConfiguration(name = "toCarEntity3")
    CarEntity toCarEntity2(CarDto carDto);

    @InheritConfiguration(name = "toCarEntity1")
    @Mappings({
        @Mapping(target = "color", ignore = true),
        @Mapping(target = "auditTrail", ignore = true),
        @Mapping(target = "primaryKey", ignore = true)
    })
    void toCarEntity3(CarDto carDto, @MappingTarget CarEntity entity);
}
