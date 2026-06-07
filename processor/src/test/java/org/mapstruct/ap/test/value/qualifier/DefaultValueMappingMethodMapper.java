/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.qualifier;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.factories.qualified.TestQualifier;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DefaultValueMappingMethodMapper {
    enum SourceType { A, B, C }

    enum TargetType { X, Y, Z }

    List<String> INVOKE_LIST = new ArrayList<>();

    DefaultValueMappingMethodMapper INSTANCE = Mappers.getMapper( DefaultValueMappingMethodMapper.class );

    @ValueMapping(source = "A", target = "X")
    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "Z", qualifiedByName = "defaultHandlerWithReturn")
    DefaultValueMappingMethodMapper.TargetType map(DefaultValueMappingMethodMapper.SourceType source);

    @ValueMapping(source = "A", target = "X")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "Z", qualifiedBy = TestQualifier.class)
    DefaultValueMappingMethodMapper.TargetType mapNoReturn(DefaultValueMappingMethodMapper.SourceType source);

    @Named("defaultHandlerWithReturn")
    default DefaultValueMappingMethodMapper.TargetType defaultHandler(
            DefaultValueMappingMethodMapper.SourceType source) {
        INVOKE_LIST.add( "defaultHandlerWithReturn" );
        return TargetType.Y;
    }

    @TestQualifier
    default void defaultHandlerNoReturn(DefaultValueMappingMethodMapper.SourceType source) {
        INVOKE_LIST.add( "defaultHandlerNoReturn" );
    }
}
