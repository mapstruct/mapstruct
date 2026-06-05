/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:54:55+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class JSpecifyUpdateMapperImpl implements JSpecifyUpdateMapper {

    @Override
    public void update(SourceBean source, TargetBean target) {
        if ( source == null ) {
            return;
        }

        target.setNonNullTarget( source.getNonNullValue() );
        String nullableValue = source.getNullableValue();
        if ( nullableValue != null ) {
            target.setNullableTarget( nullableValue );
        }
        String unannotatedValue = source.getUnannotatedValue();
        if ( unannotatedValue != null ) {
            target.setUnannotatedTarget( unannotatedValue );
        }
        String nullableValue1 = source.getNullableValue();
        if ( nullableValue1 != null ) {
            target.setNonNullTargetFromNullable( nullableValue1 );
        }
    }
}
