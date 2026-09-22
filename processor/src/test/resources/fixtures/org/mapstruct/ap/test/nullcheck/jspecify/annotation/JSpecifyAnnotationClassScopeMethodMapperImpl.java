/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.annotation;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleSource;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleTarget;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-06T18:36:41+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyAnnotationClassScopeMethodMapperImpl implements JSpecifyAnnotationClassScopeMethodMapper {

    @Override
    public SimpleTarget mapMarked(SimpleSource source) {

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( source.getName() );

        return simpleTarget;
    }

    @Override
    @NullUnmarked
    public SimpleTarget mapUnmarked(SimpleSource source) {
        if ( source == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( source.getName() );

        return simpleTarget;
    }
}
