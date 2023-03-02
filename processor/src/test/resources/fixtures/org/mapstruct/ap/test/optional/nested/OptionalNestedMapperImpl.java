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
    date = "2023-11-20T21:40:54-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class OptionalNestedMapperImpl implements OptionalNestedMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setOptionalToNonOptional( sourceOptionalToNonOptional_Value_( source ) );
        target.setOptionalToOptional( sourceOptionalToOptional_Value( source ) );
        target.setNonOptionalToNonOptional( sourceNonOptionalToNonOptional_Value( source ) );
        target.setNonOptionalToOptional( stringToStringOptional( sourceNonOptionalToOptional_Value( source ) ) );

        return target;
    }

    private String sourceOptionalToNonOptional_Value_(Source source) {
        Optional<Source.NestedOptional> optionalToNonOptional = source.getOptionalToNonOptional();
        if ( optionalToNonOptional == null ) {
            return null;
        }
        Source.NestedOptional optionalValue = optionalToNonOptional.orElse( null );
        if ( optionalValue == null ) {
            return null;
        }
        Optional<String> value = optionalValue.getValue();
        if ( value == null ) {
            return null;
        }
        return value.orElse( null );
    }

    private Optional<String> sourceOptionalToOptional_Value(Source source) {
        Optional<Source.NestedOptional> optionalToOptional = source.getOptionalToOptional();
        if ( optionalToOptional == null ) {
            return Optional.empty();
        }
        Source.NestedOptional optionalValue = optionalToOptional.orElse( null );
        if ( optionalValue == null ) {
            return Optional.empty();
        }
        return optionalValue.getValue();
    }

    private String sourceNonOptionalToNonOptional_Value(Source source) {
        Optional<Source.NestedNonOptional> nonOptionalToNonOptional = source.getNonOptionalToNonOptional();
        if ( nonOptionalToNonOptional == null ) {
            return null;
        }
        Source.NestedNonOptional optionalValue = nonOptionalToNonOptional.orElse( null );
        if ( optionalValue == null ) {
            return null;
        }
        return optionalValue.getValue();
    }

    private String sourceNonOptionalToOptional_Value(Source source) {
        Optional<Source.NestedNonOptional> nonOptionalToOptional = source.getNonOptionalToOptional();
        if ( nonOptionalToOptional == null ) {
            return null;
        }
        Source.NestedNonOptional optionalValue = nonOptionalToOptional.orElse( null );
        if ( optionalValue == null ) {
            return null;
        }
        return optionalValue.getValue();
    }

    protected Optional<String> stringToStringOptional(String string) {
        if ( string == null ) {
            return Optional.empty();
        }

        String string1 = string;
        Optional<String> optional = Optional.ofNullable( string1 );

        return optional;
    }
}
