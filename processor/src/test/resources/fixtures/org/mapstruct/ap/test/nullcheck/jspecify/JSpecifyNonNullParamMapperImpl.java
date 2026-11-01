/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NonNull;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T07:45:45+0100",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyNonNullParamMapperImpl implements JSpecifyNonNullParamMapper {

    @Override
    public TargetBean map(@NonNull SourceBean source) {

        TargetBean targetBean = new TargetBean();

        targetBean.setNonNullTarget( source.getNonNullValue() );
        targetBean.setNullableTarget( source.getNullableValue() );
        targetBean.setUnannotatedTarget( source.getUnannotatedValue() );
        if ( source.getNullableValue() != null ) {
            targetBean.setNonNullTargetFromNullable( source.getNullableValue() );
        }

        return targetBean;
    }
}
