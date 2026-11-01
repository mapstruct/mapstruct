/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.annotation;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.Nullable;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleSource;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleTarget;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-06T17:45:00+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class JSpecifyAnnotationScopeIgnoredMapperImpl implements JSpecifyAnnotationScopeIgnoredMapper {

    @Override
    public @Nullable SimpleTarget mapTo(SimpleSource simpleSource) {

        SimpleTarget simpleTarget = new SimpleTarget();

        simpleTarget.setName( simpleSource.getName() );

        return simpleTarget;
    }
}
