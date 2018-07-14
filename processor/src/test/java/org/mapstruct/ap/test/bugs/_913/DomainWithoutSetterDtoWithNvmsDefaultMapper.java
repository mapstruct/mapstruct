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
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, uses = Helper.class )
public interface DomainWithoutSetterDtoWithNvmsDefaultMapper {

    DomainWithoutSetterDtoWithNvmsDefaultMapper INSTANCE =
        Mappers.getMapper( DomainWithoutSetterDtoWithNvmsDefaultMapper.class );

    @Mappings({
        @Mapping(target = "strings", source = "strings"),
        @Mapping(target = "longs", source = "strings"),
        @Mapping(target = "stringsInitialized", source = "stringsInitialized"),
        @Mapping(target = "longsInitialized", source = "stringsInitialized"),
        @Mapping(target = "stringsWithDefault", source = "stringsWithDefault", defaultValue = "3")
   })
    DomainWithoutSetter create(Dto source);

    @InheritConfiguration( name = "create" )
    void update(Dto source, @MappingTarget DomainWithoutSetter target);

    @InheritConfiguration( name = "create" )
    DomainWithoutSetter updateWithReturn(Dto source, @MappingTarget DomainWithoutSetter target);
}
