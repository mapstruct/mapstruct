/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-12T19:58:32+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class PackageNullMarkedMapperImpl implements PackageNullMarkedMapper {

    @Override
    public PackageNullMarkedTargetBean map(PackageNullMarkedSourceBean source) {

        PackageNullMarkedTargetBean packageNullMarkedTargetBean = new PackageNullMarkedTargetBean();

        packageNullMarkedTargetBean.setValue( source.getValue() );

        return packageNullMarkedTargetBean;
    }
}
