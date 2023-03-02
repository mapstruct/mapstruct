/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-02T00:33:34-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.18 (Homebrew)"
)
public class OptionalTestMapperImpl implements OptionalTestMapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setOptionalToNonOptional( stringOptionalToString( source.getOptionalToNonOptional() ) );
        target.setNonOptionalToOptional( stringToStringOptional( source.getNonOptionalToOptional() ) );
        target.setOptionalToOptional( source.getOptionalToOptional() );
        target.setOptionalSubTypeToNonOptional( subTypeOptionalToSubType( source.getOptionalSubTypeToNonOptional() ) );
        target.setNonOptionalSubTypeToOptional( subTypeToSubTypeOptional( source.getNonOptionalSubTypeToOptional() ) );
        target.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional( source.getOptionalSubTypeToOptional() ) );

        return target;
    }

    @Override
    public Source map(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        source.setOptionalToNonOptional( stringToStringOptional( target.getOptionalToNonOptional() ) );
        source.setNonOptionalToOptional( stringOptionalToString( target.getNonOptionalToOptional() ) );
        source.setOptionalToOptional( target.getOptionalToOptional() );
        source.setOptionalSubTypeToNonOptional( subTypeToSubTypeOptional1( target.getOptionalSubTypeToNonOptional() ) );
        source.setNonOptionalSubTypeToOptional( subTypeOptionalToSubType1( target.getNonOptionalSubTypeToOptional() ) );
        source.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional1( target.getOptionalSubTypeToOptional() ) );

        return source;
    }

    @Override
    public Target.SubType map(Source.SubType source) {
        if ( source == null ) {
            return null;
        }

        String b = null;
        int value = 0;

        b = source.getA();
        value = source.getValue();

        Target.SubType subType = new Target.SubType( value, b );

        return subType;
    }

    @Override
    public Source.SubType map(Target.SubType source) {
        if ( source == null ) {
            return null;
        }

        String a = null;
        int value = 0;

        a = source.getB();
        value = source.getValue();

        Source.SubType subType = new Source.SubType( value, a );

        return subType;
    }

    protected String stringOptionalToString(Optional<String> optional) {
        if ( optional == null ) {
            return null;
        }

        return optional.map( string -> string ).orElse( null );
    }

    protected Optional<String> stringToStringOptional(String string) {
        if ( string == null ) {
            return Optional.empty();
        }

        String string1 = string;
        return Optional.ofNullable( string1 );
    }

    protected Target.SubType subTypeOptionalToSubType(Optional<Source.SubType> optional) {
        if ( optional == null ) {
            return null;
        }

        return optional.map( subType -> map( subType ) ).orElse( null );
    }

    protected Optional<Target.SubType> subTypeToSubTypeOptional(Source.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        Source.SubType subType1 = subType;
        return Optional.ofNullable( map( subType1 ) );
    }

    protected Optional<Target.SubType> subTypeOptionalToSubTypeOptional(Optional<Source.SubType> optional) {
        if ( optional == null ) {
            return Optional.empty();
        }

        return optional.map( subType -> map( subType ) );
    }

    protected Optional<Source.SubType> subTypeToSubTypeOptional1(Target.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        Target.SubType subType1 = subType;
        return Optional.ofNullable( map( subType1 ) );
    }

    protected Source.SubType subTypeOptionalToSubType1(Optional<Target.SubType> optional) {
        if ( optional == null ) {
            return null;
        }

        return optional.map( subType -> map( subType ) ).orElse( null );
    }

    protected Optional<Source.SubType> subTypeOptionalToSubTypeOptional1(Optional<Target.SubType> optional) {
        if ( optional == null ) {
            return Optional.empty();
        }

        return optional.map( subType -> map( subType ) );
    }
}
