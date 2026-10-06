/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3704;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-06T21:00:11+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
public class Issue3704MapperImpl implements Issue3704Mapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setDate( nestedOptionalToNestedOptional( source.getDate() ) );

        return target;
    }

    protected Optional<Target.Nested> nestedOptionalToNestedOptional(Optional<Source.Nested> optional) {
        if ( optional.isEmpty() ) {
            return Optional.empty();
        }

        Target.Nested nested = new Target.Nested();

        Source.Nested optionalValue = optional.get();

        nested.setValue( optionalValue.getValue() );

        return Optional.of( nested );
    }
}
