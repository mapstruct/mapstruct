/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T14:57:24+0100",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class NullMarkedSourceToPlainTargetMapperImpl implements NullMarkedSourceToPlainTargetMapper {

    @Override
    public TargetBean map(NullMarkedSourceBean source) {
        if ( source == null ) {
            return null;
        }

        TargetBean targetBean = new TargetBean();

        targetBean.setUnannotatedTarget( source.getNonNullByDefault() );
        targetBean.setNullableTarget( source.getExplicitlyNullable() );

        return targetBean;
    }
}
