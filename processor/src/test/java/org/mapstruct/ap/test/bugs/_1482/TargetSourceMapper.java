/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1482;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class TargetSourceMapper {

    public static final TargetSourceMapper INSTANCE = Mappers.getMapper( TargetSourceMapper.class );

    @Mapping(target = "wrapper", source = "bigDecimal")
    abstract Source2 map(Target target);

    protected <T extends Enum<T>> T map(String in, @TargetType Class<T>clz ) {
        if ( clz.isAssignableFrom( SourceEnum.class )) {
            return (T) SourceEnum.valueOf( in );
        }
        return null;
    }

    protected <T> ValueWrapper<T> map(T in) {
        if ( in instanceof BigDecimal ) {
            return (ValueWrapper<T>) new BigDecimalWrapper( (BigDecimal) in );

        }
        return null;
    }
}
