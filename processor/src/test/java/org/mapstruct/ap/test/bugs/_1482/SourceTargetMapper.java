/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1482;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SourceTargetMapper {

    public static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping( target = "bigDecimal", source = "wrapper" )
    abstract Target map(Source source);

    protected String map(Enum<SourceEnum> e) {
        return e.toString();
    }

    protected <T> T map(ValueWrapper<T> in) {
        return in.getValue();
    }
}
