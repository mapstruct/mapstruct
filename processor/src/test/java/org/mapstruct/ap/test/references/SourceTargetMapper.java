/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper( uses = { FooMapper.class, ReferencedCustomMapper.class } )
public interface SourceTargetMapper {
    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    Target sourceToTarget(Source source);

    List<SomeType> fromStringList(List<String> stringList);

    Map<SomeType, SomeOtherType> fromStringMap(Map<String, String> stringStringMap);
}
