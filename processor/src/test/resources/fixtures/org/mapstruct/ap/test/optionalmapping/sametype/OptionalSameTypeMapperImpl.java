package org.mapstruct.ap.test.optionalmapping.sametype;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T21:40:52-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class OptionalSameTypeMapperImpl implements OptionalSameTypeMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Optional<String> constructorOptionalToOptional = null;
        String constructorOptionalToNonOptional = null;
        Optional<String> constructorNonOptionalToOptional = null;

        constructorOptionalToOptional = source.getConstructorOptionalToOptional();
        constructorOptionalToNonOptional = stringOptionalToString( source.getConstructorOptionalToNonOptional() );
        constructorNonOptionalToOptional = stringToStringOptional( source.getConstructorNonOptionalToOptional() );

        Target target = new Target( constructorOptionalToOptional, constructorOptionalToNonOptional, constructorNonOptionalToOptional );

        target.setOptionalToOptional( source.getOptionalToOptional() );
        target.setOptionalToNonOptional( stringOptionalToString( source.getOptionalToNonOptional() ) );
        target.setNonOptionalToOptional( stringToStringOptional( source.getNonOptionalToOptional() ) );
        target.publicOptionalToOptional = source.publicOptionalToOptional;
        target.publicOptionalToNonOptional = stringOptionalToString( source.publicOptionalToNonOptional );
        target.publicNonOptionalToOptional = stringToStringOptional( source.publicNonOptionalToOptional );

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
