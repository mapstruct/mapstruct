/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T15:08:00+0100",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class NullMarkedMapperWithParamImpl implements NullMarkedMapperWithParam {

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
