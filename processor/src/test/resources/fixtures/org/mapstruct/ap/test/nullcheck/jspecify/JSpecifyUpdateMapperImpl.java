/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T15:17:03+0100",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyUpdateMapperImpl implements JSpecifyUpdateMapper {

    @Override
    public void update(SourceBean source, TargetBean target) {
        if ( source == null ) {
            return;
        }

        target.setNonNullTarget( source.getNonNullValue() );
        if ( source.getNullableValue() != null ) {
            target.setNullableTarget( source.getNullableValue() );
        }
        if ( source.getUnannotatedValue() != null ) {
            target.setUnannotatedTarget( source.getUnannotatedValue() );
        }
        if ( source.getNullableValue() != null ) {
            target.setNonNullTargetFromNullable( source.getNullableValue() );
        }
    }
}
