/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.subclass;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NonNull;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-01T18:13:02+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.13 (Eclipse Adoptium)"
)
public class JspecifyReuseMapperImpl implements JspecifyReuseMapper {

    @Override
    public AbstractTarget map(AbstractSource s) {
        if ( s == null ) {
            return null;
        }

        if (s instanceof Source1) {
            return source1ToTarget1( (Source1) s );
        }
        else {
            throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for " + s.getClass());
        }
    }

    @Override
    public TargetHolder map(SourceHolder h) {
        if ( h == null ) {
            return null;
        }

        TargetHolder targetHolder = new TargetHolder();

        targetHolder.setH( source1ToTarget1( h.getH() ) );

        return targetHolder;
    }

    protected Target1 source1ToTarget1(@NonNull Source1 source1) {

        Target1 target1 = new Target1();

        target1.setAge( source1.getAge() );
        target1.setName( source1.getName() );

        return target1;
    }
}
