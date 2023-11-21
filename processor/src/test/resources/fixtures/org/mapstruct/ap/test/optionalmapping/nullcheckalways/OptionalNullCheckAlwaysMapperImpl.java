package org.mapstruct.ap.test.optionalmapping.nullcheckalways;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T21:40:55-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class OptionalNullCheckAlwaysMapperImpl implements OptionalNullCheckAlwaysMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getOptionalToOptional() != null ) {
            target.setOptionalToOptional( source.getOptionalToOptional() );
        }
        if ( source.getOptionalToNonOptional() != null ) {
            target.setOptionalToNonOptional( stringOptionalToString( source.getOptionalToNonOptional() ) );
        }
        if ( source.getNonOptionalToOptional() != null ) {
            target.setNonOptionalToOptional( stringToStringOptional( source.getNonOptionalToOptional() ) );
        }

        return target;
    }

    protected String stringOptionalToString(Optional<String> optional) {
        if ( optional == null ) {
            return null;
        }

        String string1 = optional.map( string -> string ).orElse( null );

        return string1;
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
