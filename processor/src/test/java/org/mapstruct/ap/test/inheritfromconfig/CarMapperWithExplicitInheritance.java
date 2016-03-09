/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(
    config = AutoInheritedConfig.class,
    mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT
)
public interface CarMapperWithExplicitInheritance {
    CarMapperWithExplicitInheritance INSTANCE = Mappers.getMapper( CarMapperWithExplicitInheritance.class );

    @InheritConfiguration(name = "baseDtoToEntity")
    @Mapping(target = "color", source = "colour")
    CarEntity toCarEntity(CarDto carDto);

    @InheritInverseConfiguration(name = "toCarEntity")
    CarDto toCarDto(CarEntity entity);

    @InheritConfiguration(name = "toCarEntity")
    @Mapping(target = "auditTrail", constant = "fixed")
    CarEntity toCarEntityWithFixedAuditTrail(CarDto carDto);
}
