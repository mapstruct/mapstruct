/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3142;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3142WithAfterMappingExceptionMapper {

    Issue3142WithAfterMappingExceptionMapper INSTANCE =
        Mappers.getMapper( Issue3142WithAfterMappingExceptionMapper.class );

    Target map(Source source, String id) throws Issue3142Exception;

    @AfterMapping
    default void afterMappingValidation(Object source) throws Issue3142Exception {
        if ( source instanceof Source.Nested ) {
            Source.Nested nested = (Source.Nested) source;
            if ( "throwException".equals( nested.getValue() ) ) {
                throw new Issue3142Exception( "Source nested exception" );
            }
        }
    }
}
