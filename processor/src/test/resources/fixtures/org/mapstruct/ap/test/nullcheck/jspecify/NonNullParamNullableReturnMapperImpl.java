/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-15T00:00:00+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class NonNullParamNullableReturnMapperImpl implements NonNullParamNullableReturnMapper {

    @Override
    public TargetBean map(SourceBean source) {
        if ( source == null ) {
            return null;
        }

        TargetBean targetBean = new TargetBean();

        if ( source.getNullableValue() != null ) {
            String nonNullTargetFromNullable = transform( source.getNullableValue() );
            if ( nonNullTargetFromNullable != null ) {
                targetBean.setNonNullTargetFromNullable( nonNullTargetFromNullable );
            }
        }

        return targetBean;
    }
}
