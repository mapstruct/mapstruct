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
    date = "2023-03-03T00:01:21-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.18 (Homebrew)"
)
public class SimpleOptionalTestMapperImpl implements SimpleOptionalTestMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        String constructorOptionalToNonOptional = null;
        Optional<String> constructorNonOptionalToOptional = null;
        Optional<String> constructorOptionalToOptional = null;
        Target.SubType constructorOptionalSubTypeToNonOptional = null;
        Optional<Target.SubType> constructorNonOptionalSubTypeToOptional = null;
        Optional<Target.SubType> constructorOptionalSubTypeToOptional = null;

        constructorOptionalToNonOptional = stringOptionalToString( source.getConstructorOptionalToNonOptional() );
        constructorNonOptionalToOptional = stringToStringOptional( source.getConstructorNonOptionalToOptional() );
        constructorOptionalToOptional = source.getConstructorOptionalToOptional();
        constructorOptionalSubTypeToNonOptional = subTypeOptionalToSubType( source.getConstructorOptionalSubTypeToNonOptional() );
        constructorNonOptionalSubTypeToOptional = subTypeToSubTypeOptional( source.getConstructorNonOptionalSubTypeToOptional() );
        constructorOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional( source.getConstructorOptionalSubTypeToOptional() );

        Target target = new Target( constructorOptionalToNonOptional, constructorNonOptionalToOptional, constructorOptionalToOptional, constructorOptionalSubTypeToNonOptional, constructorNonOptionalSubTypeToOptional, constructorOptionalSubTypeToOptional );

        target.setOptionalToNonOptional( stringOptionalToString( source.getOptionalToNonOptional() ) );
        target.setNonOptionalToOptional( stringToStringOptional( source.getNonOptionalToOptional() ) );
        target.setOptionalToOptional( source.getOptionalToOptional() );
        target.setOptionalSubTypeToNonOptional( subTypeOptionalToSubType( source.getOptionalSubTypeToNonOptional() ) );
        target.setNonOptionalSubTypeToOptional( subTypeToSubTypeOptional( source.getNonOptionalSubTypeToOptional() ) );
        target.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional( source.getOptionalSubTypeToOptional() ) );
        target.setMonitoredOptionalToString( stringOptionalToString( source.getMonitoredOptionalToString() ) );
        target.setMonitoredOptionalSubTypeToSubType( subTypeOptionalToSubType( source.getMonitoredOptionalSubTypeToSubType() ) );
        target.publicOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional( source.publicOptionalSubTypeToOptional );
        target.publicOptionalToNonOptionalWithDefault = stringOptionalToString( source.publicOptionalToNonOptionalWithDefault );

        return target;
    }

    @Override
    public Source fromTarget(Target target) {
        if ( target == null ) {
            return null;
        }

        Optional<String> constructorOptionalToNonOptional = null;
        String constructorNonOptionalToOptional = null;
        Optional<String> constructorOptionalToOptional = null;
        Optional<Source.SubType> constructorOptionalSubTypeToNonOptional = null;
        Source.SubType constructorNonOptionalSubTypeToOptional = null;
        Optional<Source.SubType> constructorOptionalSubTypeToOptional = null;

        constructorOptionalToNonOptional = stringToStringOptional( target.getConstructorOptionalToNonOptional() );
        constructorNonOptionalToOptional = stringOptionalToString( target.getConstructorNonOptionalToOptional() );
        constructorOptionalToOptional = target.getConstructorOptionalToOptional();
        constructorOptionalSubTypeToNonOptional = subTypeToSubTypeOptional1( target.getConstructorOptionalSubTypeToNonOptional() );
        constructorNonOptionalSubTypeToOptional = subTypeOptionalToSubType1( target.getConstructorNonOptionalSubTypeToOptional() );
        constructorOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional1( target.getConstructorOptionalSubTypeToOptional() );

        Source source = new Source( constructorOptionalToNonOptional, constructorNonOptionalToOptional, constructorOptionalToOptional, constructorOptionalSubTypeToNonOptional, constructorNonOptionalSubTypeToOptional, constructorOptionalSubTypeToOptional );

        source.setOptionalToNonOptional( stringToStringOptional( target.getOptionalToNonOptional() ) );
        source.setNonOptionalToOptional( stringOptionalToString( target.getNonOptionalToOptional() ) );
        source.setOptionalToOptional( target.getOptionalToOptional() );
        source.setOptionalSubTypeToNonOptional( subTypeToSubTypeOptional1( target.getOptionalSubTypeToNonOptional() ) );
        source.setNonOptionalSubTypeToOptional( subTypeOptionalToSubType1( target.getNonOptionalSubTypeToOptional() ) );
        source.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional1( target.getOptionalSubTypeToOptional() ) );
        source.setMonitoredOptionalToString( stringToStringOptional( target.getMonitoredOptionalToString() ) );
        source.setMonitoredOptionalSubTypeToSubType( subTypeToSubTypeOptional1( target.getMonitoredOptionalSubTypeToSubType() ) );
        source.publicOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional1( target.publicOptionalSubTypeToOptional );
        source.publicOptionalToNonOptionalWithDefault = stringToStringOptional( target.publicOptionalToNonOptionalWithDefault );

        return source;
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

    protected Target.SubType subTypeToSubType(Source.SubType subType) {
        if ( subType == null ) {
            return null;
        }

        int value = 0;

        value = subType.getValue();

        Target.SubType subType1 = new Target.SubType( value );

        return subType1;
    }

    protected Target.SubType subTypeOptionalToSubType(Optional<Source.SubType> optional) {
        if ( optional == null ) {
            return null;
        }

        return optional.map( subType -> subTypeToSubType( subType ) ).orElse( null );
    }

    protected Optional<Target.SubType> subTypeToSubTypeOptional(Source.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        Source.SubType subType1 = subType;
        return Optional.ofNullable( subTypeToSubType( subType1 ) );
    }

    protected Optional<Target.SubType> subTypeOptionalToSubTypeOptional(Optional<Source.SubType> optional) {
        if ( optional == null ) {
            return Optional.empty();
        }

        return optional.map( subType -> subTypeToSubType( subType ) );
    }

    protected Source.SubType subTypeToSubType1(Target.SubType subType) {
        if ( subType == null ) {
            return null;
        }

        int value = 0;

        value = subType.getValue();

        Source.SubType subType1 = new Source.SubType( value );

        return subType1;
    }

    protected Optional<Source.SubType> subTypeToSubTypeOptional1(Target.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        Target.SubType subType1 = subType;
        return Optional.ofNullable( subTypeToSubType1( subType1 ) );
    }

    protected Source.SubType subTypeOptionalToSubType1(Optional<Target.SubType> optional) {
        if ( optional == null ) {
            return null;
        }

        return optional.map( subType -> subTypeToSubType1( subType ) ).orElse( null );
    }

    protected Optional<Source.SubType> subTypeOptionalToSubTypeOptional1(Optional<Target.SubType> optional) {
        if ( optional == null ) {
            return Optional.empty();
        }

        return optional.map( subType -> subTypeToSubType1( subType ) );
    }
}
