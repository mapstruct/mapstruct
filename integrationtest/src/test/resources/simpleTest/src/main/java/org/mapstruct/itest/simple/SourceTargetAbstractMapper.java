/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper( uses = ReferencedCustomMapper.class )
public abstract class SourceTargetAbstractMapper {

    public static SourceTargetAbstractMapper INSTANCE = Mappers.getMapper( SourceTargetAbstractMapper.class );

    @Mapping(source = "qax", target = "baz")
    @Mapping(source = "baz", target = "qax")
    @Mapping(source = "forNested.value", target = "fromNested")
    public abstract Target sourceToTarget(Source source);

    @Mapping(target = "forNested", ignore = true)
    @Mapping(target = "extendsBound", ignore = true)
    public abstract Source targetToSource(Target target);

    protected void isNeverCalled(Source source) {
        throw new RuntimeException("not expected to be called");
    }
}
