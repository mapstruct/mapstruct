/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.qualifier;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-21T15:20:53+0800",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 23.0.2 (Homebrew)"
)
public class DefaultValueMappingMethodMapperImpl implements DefaultValueMappingMethodMapper {

    @Override
    public DefaultValueMappingMethodMapper.TargetType map(DefaultValueMappingMethodMapper.SourceType source) {
        if ( source == null ) {
            return null;
        }

        DefaultValueMappingMethodMapper.TargetType targetType;

        switch ( source ) {
            case A: targetType = DefaultValueMappingMethodMapper.TargetType.X;
            break;
            default: return defaultHandler( source );
        }

        return targetType;
    }

    @Override
    public DefaultValueMappingMethodMapper.TargetType mapNoReturn(DefaultValueMappingMethodMapper.SourceType source) {
        if ( source == null ) {
            return null;
        }

        DefaultValueMappingMethodMapper.TargetType targetType;

        switch ( source ) {
            case A: targetType = DefaultValueMappingMethodMapper.TargetType.X;
            break;
            default:
            defaultHandlerNoReturn( source );
            targetType = DefaultValueMappingMethodMapper.TargetType.Z;
        }

        return targetType;
    }
}
