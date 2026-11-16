/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-14T23:41:40+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyNonNullReturnBeanMapperImpl implements JSpecifyNonNullReturnBeanMapper {

    @Override
    public NullMarkedTargetBean map(JSpecifyNonNullReturnBeanSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        if ( source != null ) {
            nullMarkedTargetBean.setNonNullByDefault( source.getValue() );
        }

        return nullMarkedTargetBean;
    }
}
