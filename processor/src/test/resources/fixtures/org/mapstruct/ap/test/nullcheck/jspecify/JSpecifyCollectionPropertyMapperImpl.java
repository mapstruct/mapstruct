/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-14T23:05:10+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyCollectionPropertyMapperImpl implements JSpecifyCollectionPropertyMapper {

    @Override
    public NullMarkedCollectionTargetBean map(NullMarkedCollectionSourceBean source) {

        NullMarkedCollectionTargetBean nullMarkedCollectionTargetBean = new NullMarkedCollectionTargetBean();

        nullMarkedCollectionTargetBean.setValues( source.getValues() );

        return nullMarkedCollectionTargetBean;
    }
}
