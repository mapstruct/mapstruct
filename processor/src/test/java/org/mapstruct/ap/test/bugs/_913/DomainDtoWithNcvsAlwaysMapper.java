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
package org.mapstruct.ap.test.bugs._913;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = Helper.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface DomainDtoWithNcvsAlwaysMapper {

    DomainDtoWithNcvsAlwaysMapper INSTANCE = Mappers.getMapper( DomainDtoWithNcvsAlwaysMapper.class );

    @Mappings({
        @Mapping(target = "strings", source = "strings"),
        @Mapping(target = "longs", source = "strings"),
        @Mapping(target = "stringsInitialized", source = "stringsInitialized"),
        @Mapping(target = "longsInitialized", source = "stringsInitialized"),
        @Mapping(target = "stringsWithDefault", source = "stringsWithDefault", defaultValue = "3")
    })
    Domain create(DtoWithPresenceCheck source);

    @InheritConfiguration( name = "create" )
    void update(DtoWithPresenceCheck source, @MappingTarget Domain target);

    @InheritConfiguration( name = "create" )
    Domain updateWithReturn(DtoWithPresenceCheck source, @MappingTarget Domain target);
}
