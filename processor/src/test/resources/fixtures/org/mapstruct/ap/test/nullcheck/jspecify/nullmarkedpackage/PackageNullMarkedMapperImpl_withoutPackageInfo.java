/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:55:05+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class PackageNullMarkedMapperImpl implements PackageNullMarkedMapper {

    @Override
    public PackageNullMarkedTargetBean map(PackageNullMarkedSourceBean source) {
        if ( source == null ) {
            return null;
        }

        PackageNullMarkedTargetBean packageNullMarkedTargetBean = new PackageNullMarkedTargetBean();

        String value = source.getValue();
        if ( value != null ) {
            packageNullMarkedTargetBean.setValue( value );
        }

        return packageNullMarkedTargetBean;
    }
}
