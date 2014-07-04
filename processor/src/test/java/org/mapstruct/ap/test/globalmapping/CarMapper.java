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
package org.mapstruct.ap.test.globalmapping;

import org.mapstruct.GlobalMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    config = DictionaryConfigHolder.class,
    globalMappings = {
        @GlobalMapping(
            sourceType = BaseVehicleDto.class,
            targetType = BaseVehicleEntity.class,
            mappings = @Mapping(source = "id", target = "primaryKey")
        ),
        @GlobalMapping(
            targetType = BaseVehicleEntity.class,
            mappings = @Mapping(target = "auditTrail", ignore = true)
        )
    }
)
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    CarEntity toCarEntity(CarDto carDto);

    @InheritInverseConfiguration( name = "toCarEntity" )
    CarDto toCarDto(CarEntity entity);

    @Mapping( target = "auditTrail", constant = "fixed" )
    CarEntity toCarEntityWithFixedAuditTrail(CarDto carDto);
}
