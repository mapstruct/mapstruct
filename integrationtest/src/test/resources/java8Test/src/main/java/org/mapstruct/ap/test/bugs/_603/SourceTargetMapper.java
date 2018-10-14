/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._603;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(SourceTargetMapperDecorator.class)
public interface SourceTargetMapper {
    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping(target = "value1", expression = "java( SourceTargetMapper.mapA() )")
    @Mapping(target = "value2", expression = "java( this.mapB() )")
    Target mapSourceToTarget(Source source);

    static String mapA() {
        return "foo";
    }

    default String mapB() {
        return "bar";
    }
}
