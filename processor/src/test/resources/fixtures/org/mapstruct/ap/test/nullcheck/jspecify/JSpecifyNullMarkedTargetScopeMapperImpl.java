/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-06T21:16:16+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyNullMarkedTargetScopeMapperImpl implements JSpecifyNullMarkedTargetScopeMapper {

    @Override
    public NullMarkedTargetBean map(SourceBean source) {
        if ( source == null ) {
            return null;
        }

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        if ( source.getNullableValue() != null ) {
            nullMarkedTargetBean.setNonNullByDefault( source.getNullableValue() );
        }

        return nullMarkedTargetBean;
    }
}
