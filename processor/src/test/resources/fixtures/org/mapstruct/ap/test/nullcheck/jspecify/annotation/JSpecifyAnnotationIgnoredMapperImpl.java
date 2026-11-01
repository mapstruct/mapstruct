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
    date = "2026-09-06T17:59:44+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class JSpecifyAnnotationIgnoredMapperImpl implements JSpecifyAnnotationIgnoredMapper {

    @Override
    public SimpleTarget reflectDefaultState(SimpleSource simpleSource) {
        if ( simpleSource == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( simpleSource.getName() );

        return simpleTarget;
    }

    @Override
    public SimpleTarget cancelingAnnotations(SimpleSource simpleSource) {
        if ( simpleSource == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( simpleSource.getName() );

        return simpleTarget;
    }

    @Override
    public SimpleTarget cancelingScopeAnnotations(SimpleSource simpleSource) {
        if ( simpleSource == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( simpleSource.getName() );

        return simpleTarget;
    }

    @Override
    public SimpleTarget cancelingScopeAnnotationsWithDefaultState(SimpleSource simpleSource) {
        if ( simpleSource == null ) {
            return null;
        }

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( simpleSource.getName() );

        return simpleTarget;
    }
}
