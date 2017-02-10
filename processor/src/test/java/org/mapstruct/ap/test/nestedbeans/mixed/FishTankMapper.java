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
package org.mapstruct.ap.test.nestedbeans.mixed;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishTankDto;
import org.mapstruct.ap.test.nestedbeans.mixed.source.FishTank;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface FishTankMapper {

    FishTankMapper INSTANCE = Mappers.getMapper( FishTankMapper.class );

    @Mappings({
        @Mapping(target = "fish.kind", source = "fish.type"),
        @Mapping(target = "fish.name", ignore = true),
        @Mapping(target = "ornament", source = "interior.ornament"),
        @Mapping(target = "material.materialType", source = "material"),
        @Mapping(target = "quality.report.organisation.name", source = "quality.report.organisationName")
    })
    FishTankDto map( FishTank source  );

    @Mappings({
        @Mapping(target = "fish.kind", source = "source.fish.type"),
        @Mapping(target = "fish.name", ignore = true),
        @Mapping(target = "ornament", source = "source.interior.ornament"),
        @Mapping(target = "material.materialType", source = "source.material"),
        @Mapping(target = "quality.report.organisation.name", source = "source.quality.report.organisationName")
   })
    FishTankDto mapAsWell( FishTank source  );

    @InheritInverseConfiguration( name = "map" )
    FishTank map( FishTankDto source  );

}
