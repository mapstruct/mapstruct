/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:55:02+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class JSpecifyNonNullParamMapperImpl implements JSpecifyNonNullParamMapper {

    @Override
    public TargetBean map(SourceBean source) {

        TargetBean targetBean = new TargetBean();

        targetBean.setNonNullTarget( source.getNonNullValue() );
        targetBean.setNullableTarget( source.getNullableValue() );
        targetBean.setUnannotatedTarget( source.getUnannotatedValue() );
        String nullableValue1 = source.getNullableValue();
        if ( nullableValue1 != null ) {
            targetBean.setNonNullTargetFromNullable( nullableValue1 );
        }

        return targetBean;
    }
}
