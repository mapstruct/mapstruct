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
    date = "2023-06-30T00:29:43-0400",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class NullValueCheckAlwaysOptionalTestMapperImpl implements NullValueCheckAlwaysOptionalTestMapper {

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

        if ( source.getConstructorOptionalToNonOptional() != null && source.getConstructorOptionalToNonOptional().isPresent() ) {
            constructorOptionalToNonOptional = stringOptionalToString( source.getConstructorOptionalToNonOptional() );
        }
        if ( source.getConstructorNonOptionalToOptional() != null ) {
            constructorNonOptionalToOptional = stringToStringOptional( source.getConstructorNonOptionalToOptional() );
        }
        else {
            constructorNonOptionalToOptional = Optional.empty();
        }
        if ( source.getConstructorOptionalToOptional() != null && source.getConstructorOptionalToOptional().isPresent() ) {
            constructorOptionalToOptional = source.getConstructorOptionalToOptional();
        }
        else {
            constructorOptionalToOptional = Optional.empty();
        }
        if ( source.getConstructorOptionalSubTypeToNonOptional() != null && source.getConstructorOptionalSubTypeToNonOptional().isPresent() ) {
            constructorOptionalSubTypeToNonOptional = subTypeOptionalToSubType( source.getConstructorOptionalSubTypeToNonOptional() );
        }
        if ( source.getConstructorNonOptionalSubTypeToOptional() != null ) {
            constructorNonOptionalSubTypeToOptional = subTypeToSubTypeOptional( source.getConstructorNonOptionalSubTypeToOptional() );
        }
        else {
            constructorNonOptionalSubTypeToOptional = Optional.empty();
        }
        if ( source.getConstructorOptionalSubTypeToOptional() != null && source.getConstructorOptionalSubTypeToOptional().isPresent() ) {
            constructorOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional( source.getConstructorOptionalSubTypeToOptional() );
        }
        else {
            constructorOptionalSubTypeToOptional = Optional.empty();
        }

        Target target = new Target( constructorOptionalToNonOptional, constructorNonOptionalToOptional, constructorOptionalToOptional, constructorOptionalSubTypeToNonOptional, constructorNonOptionalSubTypeToOptional, constructorOptionalSubTypeToOptional );

        if ( source.getOptionalToNonOptional() != null && source.getOptionalToNonOptional().isPresent() ) {
            target.setOptionalToNonOptional( stringOptionalToString( source.getOptionalToNonOptional() ) );
        }
        if ( source.getNonOptionalToOptional() != null ) {
            target.setNonOptionalToOptional( stringToStringOptional( source.getNonOptionalToOptional() ) );
        }
        else {
            target.setNonOptionalToOptional( Optional.empty() );
        }
        if ( source.getOptionalToOptional() != null && source.getOptionalToOptional().isPresent() ) {
            target.setOptionalToOptional( source.getOptionalToOptional() );
        }
        else {
            target.setOptionalToOptional( Optional.empty() );
        }
        if ( source.getOptionalSubTypeToNonOptional() != null && source.getOptionalSubTypeToNonOptional().isPresent() ) {
            target.setOptionalSubTypeToNonOptional( subTypeOptionalToSubType( source.getOptionalSubTypeToNonOptional() ) );
        }
        if ( source.getNonOptionalSubTypeToOptional() != null ) {
            target.setNonOptionalSubTypeToOptional( subTypeToSubTypeOptional( source.getNonOptionalSubTypeToOptional() ) );
        }
        else {
            target.setNonOptionalSubTypeToOptional( Optional.empty() );
        }
        if ( source.getOptionalSubTypeToOptional() != null && source.getOptionalSubTypeToOptional().isPresent() ) {
            target.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional( source.getOptionalSubTypeToOptional() ) );
        }
        else {
            target.setOptionalSubTypeToOptional( Optional.empty() );
        }
        if ( source.getMonitoredOptionalToString() != null && source.getMonitoredOptionalToString().isPresent() ) {
            target.setMonitoredOptionalToString( stringOptionalToString( source.getMonitoredOptionalToString() ) );
        }
        if ( source.getMonitoredOptionalSubTypeToSubType() != null && source.getMonitoredOptionalSubTypeToSubType().isPresent() ) {
            target.setMonitoredOptionalSubTypeToSubType( subTypeOptionalToSubType( source.getMonitoredOptionalSubTypeToSubType() ) );
        }
        if ( source.publicOptionalSubTypeToOptional != null && source.publicOptionalSubTypeToOptional.isPresent() ) {
            target.publicOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional( source.publicOptionalSubTypeToOptional );
        }
        else {
            target.publicOptionalSubTypeToOptional = Optional.empty();
        }
        if ( source.publicOptionalToNonOptionalWithDefault != null && source.publicOptionalToNonOptionalWithDefault.isPresent() ) {
            target.publicOptionalToNonOptionalWithDefault = stringOptionalToString( source.publicOptionalToNonOptionalWithDefault );
        }

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

        if ( target.getConstructorOptionalToNonOptional() != null ) {
            constructorOptionalToNonOptional = stringToStringOptional( target.getConstructorOptionalToNonOptional() );
        }
        else {
            constructorOptionalToNonOptional = Optional.empty();
        }
        if ( target.getConstructorNonOptionalToOptional() != null && target.getConstructorNonOptionalToOptional().isPresent() ) {
            constructorNonOptionalToOptional = stringOptionalToString( target.getConstructorNonOptionalToOptional() );
        }
        if ( target.getConstructorOptionalToOptional() != null && target.getConstructorOptionalToOptional().isPresent() ) {
            constructorOptionalToOptional = target.getConstructorOptionalToOptional();
        }
        else {
            constructorOptionalToOptional = Optional.empty();
        }
        if ( target.getConstructorOptionalSubTypeToNonOptional() != null ) {
            constructorOptionalSubTypeToNonOptional = subTypeToSubTypeOptional1( target.getConstructorOptionalSubTypeToNonOptional() );
        }
        else {
            constructorOptionalSubTypeToNonOptional = Optional.empty();
        }
        if ( target.getConstructorNonOptionalSubTypeToOptional() != null && target.getConstructorNonOptionalSubTypeToOptional().isPresent() ) {
            constructorNonOptionalSubTypeToOptional = subTypeOptionalToSubType1( target.getConstructorNonOptionalSubTypeToOptional() );
        }
        if ( target.getConstructorOptionalSubTypeToOptional() != null && target.getConstructorOptionalSubTypeToOptional().isPresent() ) {
            constructorOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional1( target.getConstructorOptionalSubTypeToOptional() );
        }
        else {
            constructorOptionalSubTypeToOptional = Optional.empty();
        }

        Source source = new Source( constructorOptionalToNonOptional, constructorNonOptionalToOptional, constructorOptionalToOptional, constructorOptionalSubTypeToNonOptional, constructorNonOptionalSubTypeToOptional, constructorOptionalSubTypeToOptional );

        if ( target.getOptionalToNonOptional() != null ) {
            source.setOptionalToNonOptional( stringToStringOptional( target.getOptionalToNonOptional() ) );
        }
        else {
            source.setOptionalToNonOptional( Optional.empty() );
        }
        if ( target.getNonOptionalToOptional() != null && target.getNonOptionalToOptional().isPresent() ) {
            source.setNonOptionalToOptional( stringOptionalToString( target.getNonOptionalToOptional() ) );
        }
        if ( target.getOptionalToOptional() != null && target.getOptionalToOptional().isPresent() ) {
            source.setOptionalToOptional( target.getOptionalToOptional() );
        }
        else {
            source.setOptionalToOptional( Optional.empty() );
        }
        if ( target.getOptionalSubTypeToNonOptional() != null ) {
            source.setOptionalSubTypeToNonOptional( subTypeToSubTypeOptional1( target.getOptionalSubTypeToNonOptional() ) );
        }
        else {
            source.setOptionalSubTypeToNonOptional( Optional.empty() );
        }
        if ( target.getNonOptionalSubTypeToOptional() != null && target.getNonOptionalSubTypeToOptional().isPresent() ) {
            source.setNonOptionalSubTypeToOptional( subTypeOptionalToSubType1( target.getNonOptionalSubTypeToOptional() ) );
        }
        if ( target.getOptionalSubTypeToOptional() != null && target.getOptionalSubTypeToOptional().isPresent() ) {
            source.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional1( target.getOptionalSubTypeToOptional() ) );
        }
        else {
            source.setOptionalSubTypeToOptional( Optional.empty() );
        }
        if ( target.getMonitoredOptionalToString() != null ) {
            source.setMonitoredOptionalToString( stringToStringOptional( target.getMonitoredOptionalToString() ) );
        }
        else {
            source.setMonitoredOptionalToString( Optional.empty() );
        }
        if ( target.getMonitoredOptionalSubTypeToSubType() != null ) {
            source.setMonitoredOptionalSubTypeToSubType( subTypeToSubTypeOptional1( target.getMonitoredOptionalSubTypeToSubType() ) );
        }
        else {
            source.setMonitoredOptionalSubTypeToSubType( Optional.empty() );
        }
        if ( target.publicOptionalSubTypeToOptional != null && target.publicOptionalSubTypeToOptional.isPresent() ) {
            source.publicOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional1( target.publicOptionalSubTypeToOptional );
        }
        else {
            source.publicOptionalSubTypeToOptional = Optional.empty();
        }
        if ( target.publicOptionalToNonOptionalWithDefault != null ) {
            source.publicOptionalToNonOptionalWithDefault = stringToStringOptional( target.publicOptionalToNonOptionalWithDefault );
        }
        else {
            source.publicOptionalToNonOptionalWithDefault = Optional.empty();
        }

        return source;
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

    protected Target.SubType subTypeToSubType(Source.SubType subType) {
        beforeNonOptionalSourceWithNoTargetType( subType );
        beforeNonOptionalSourceWithNonOptionalTargetType( Target.SubType.class, subType );

        if ( subType == null ) {
            return null;
        }

        int value = 0;

        value = subType.getValue();

        Target.SubType subType1 = new Target.SubType( value );

        afterNonOptionalSource( subType );

        return subType1;
    }

    protected Target.SubType subTypeOptionalToSubType(Optional<Source.SubType> optional) {
        beforeOptionalSourceWithNoTargetType( optional );
        beforeOptionalSourceWithNonOptionalTargetType( Target.SubType.class, optional );

        if ( optional == null ) {
            return null;
        }

        Target.SubType subType1 = optional.map( subType -> subTypeToSubType( subType ) ).orElse( null );

        afterOptionalSource( optional );
        afterOptionalSourceWithNonOptionalTarget( subType1, optional );

        return subType1;
    }

    protected Optional<Target.SubType> subTypeToSubTypeOptional(Source.SubType subType) {
        beforeNonOptionalSourceWithNoTargetType( subType );

        if ( subType == null ) {
            return Optional.empty();
        }

        Source.SubType subType1 = subType;
        Optional<Target.SubType> optional = Optional.ofNullable( subTypeToSubType( subType1 ) );

        afterNonOptionalSource( subType );
        afterNonOptionalSourceOptionalTarget( optional, subType );

        return optional;
    }

    protected Optional<Target.SubType> subTypeOptionalToSubTypeOptional(Optional<Source.SubType> optional) {
        beforeOptionalSourceWithNoTargetType( optional );

        if ( optional == null ) {
            return Optional.empty();
        }

        Optional<Target.SubType> optional1 = optional.map( subType -> subTypeToSubType( subType ) );

        afterOptionalSource( optional );
        afterOptionalSourceWithOptionalTarget( optional1, optional );

        return optional1;
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
        Optional<Source.SubType> optional = Optional.ofNullable( subTypeToSubType1( subType1 ) );

        return optional;
    }

    protected Source.SubType subTypeOptionalToSubType1(Optional<Target.SubType> optional) {
        if ( optional == null ) {
            return null;
        }

        Source.SubType subType1 = optional.map( subType -> subTypeToSubType1( subType ) ).orElse( null );

        return subType1;
    }

    protected Optional<Source.SubType> subTypeOptionalToSubTypeOptional1(Optional<Target.SubType> optional) {
        if ( optional == null ) {
            return Optional.empty();
        }

        Optional<Source.SubType> optional1 = optional.map( subType -> subTypeToSubType1( subType ) );

        return optional1;
    }
}
