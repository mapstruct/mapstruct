/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T22:24:30+0530",
    comments = "version: , compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
public class JSpecifyNullableInputNonNullReturnConstructorMapperImpl implements JSpecifyNullableInputNonNullReturnConstructorMapper {

    @Override
    public ReusedConstructorTarget map(ReusedConstructorSource source) {
        if ( source == null ) {
            return null;
        }

        ReusedTargetValue value = null;

        value = map( source.getValue() );

        ReusedConstructorTarget reusedConstructorTarget = new ReusedConstructorTarget( value );

        return reusedConstructorTarget;
    }
}
