/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._913;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = Helper.class)
// this is the default nvms, so no need to define
public interface DomainWithoutSetterDtoWithPresenceCheckMapper {

    DomainWithoutSetterDtoWithPresenceCheckMapper INSTANCE
        = Mappers.getMapper( DomainWithoutSetterDtoWithPresenceCheckMapper.class );

    @Mapping(target = "strings", source = "strings")
    @Mapping(target = "longs", source = "strings")
    @Mapping(target = "stringsInitialized", source = "stringsInitialized")
    @Mapping(target = "longsInitialized", source = "stringsInitialized")
    @Mapping(target = "stringsWithDefault", source = "stringsWithDefault", defaultValue = "3")
    DomainWithoutSetter create(DtoWithPresenceCheck source);

    @InheritConfiguration( name = "create" )
    void update(DtoWithPresenceCheck source, @MappingTarget DomainWithoutSetter target);

    @InheritConfiguration( name = "create" )
    DomainWithoutSetter updateWithReturn(DtoWithPresenceCheck source, @MappingTarget DomainWithoutSetter target);
}
