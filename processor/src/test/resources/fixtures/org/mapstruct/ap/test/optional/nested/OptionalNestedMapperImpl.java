/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nested;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-23T10:02:41+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
public class OptionalNestedMapperImpl implements OptionalNestedMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<String> value = sourceOptionalToNonOptionalValue( source );
        if ( value.isPresent() ) {
            target.setOptionalToNonOptional( value.get() );
        }
        target.setOptionalToOptional( sourceOptionalToOptionalValue( source ) );
        target.setNonOptionalToNonOptional( sourceNonOptionalToNonOptionalValue( source ) );
        String value3 = sourceNonOptionalToOptionalValue( source );
        if ( value3 != null ) {
            target.setNonOptionalToOptional( Optional.of( value3 ) );
        }

        return target;
    }

    private Optional<String> sourceOptionalToNonOptionalValue(Source source) {
        Optional<Source.NestedOptional> optionalToNonOptional = source.getOptionalToNonOptional();
        if ( optionalToNonOptional.isEmpty() ) {
            return Optional.empty();
        }
        return optionalToNonOptional.get().getValue();
    }

    private Optional<String> sourceOptionalToOptionalValue(Source source) {
        Optional<Source.NestedOptional> optionalToOptional = source.getOptionalToOptional();
        if ( optionalToOptional.isEmpty() ) {
            return Optional.empty();
        }
        return optionalToOptional.get().getValue();
    }

    private String sourceNonOptionalToNonOptionalValue(Source source) {
        Optional<Source.NestedNonOptional> nonOptionalToNonOptional = source.getNonOptionalToNonOptional();
        if ( nonOptionalToNonOptional.isEmpty() ) {
            return null;
        }
        return nonOptionalToNonOptional.get().getValue();
    }

    private String sourceNonOptionalToOptionalValue(Source source) {
        Optional<Source.NestedNonOptional> nonOptionalToOptional = source.getNonOptionalToOptional();
        if ( nonOptionalToOptional.isEmpty() ) {
            return null;
        }
        return nonOptionalToOptional.get().getValue();
    }
}
