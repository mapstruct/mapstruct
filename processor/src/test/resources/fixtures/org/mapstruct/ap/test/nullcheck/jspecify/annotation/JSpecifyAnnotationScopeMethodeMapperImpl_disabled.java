/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.annotation;

import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleSource;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleTarget;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-18T18:53:04+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class JSpecifyAnnotationScopeMethodeMapperImpl implements JSpecifyAnnotationScopeMethodeMapper {

    @Override
    public SimpleTarget mapToNonNul(SimpleSource simpleSource) {
        if ( simpleSource == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( simpleSource.getName() );

        return simpleTarget;
    }

    @Override
    public SimpleTarget mapToNullMarked(SimpleSource simpleSource) {
        if ( simpleSource == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( simpleSource.getName() );

        return simpleTarget;
    }
}
