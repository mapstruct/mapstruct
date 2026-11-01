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
    date = "2026-10-18T18:53:01+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class JSpecifyAnnotationClassScopeMethodMapperImpl implements JSpecifyAnnotationClassScopeMethodMapper {

    @Override
    public SimpleTarget mapMarked(SimpleSource source) {
        if ( source == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( source.getName() );

        return simpleTarget;
    }

    @Override
    public SimpleTarget mapUnmarked(SimpleSource source) {
        if ( source == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( source.getName() );

        return simpleTarget;
    }
}
