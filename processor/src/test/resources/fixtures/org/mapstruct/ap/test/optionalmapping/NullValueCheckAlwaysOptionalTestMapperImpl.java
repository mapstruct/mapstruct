package org.mapstruct.ap.test.optionalmapping;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-02T03:14:36-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.18 (Homebrew)"
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

        if ( source.getConstructorOptionalToNonOptional() != null ) {
            constructorOptionalToNonOptional = stringOptionalToString( source.getConstructorOptionalToNonOptional() );
        }
        if ( source.getConstructorNonOptionalToOptional() != null ) {
            constructorNonOptionalToOptional = stringToStringOptional( source.getConstructorNonOptionalToOptional() );
        }
        else {
            constructorNonOptionalToOptional = Optional.empty();
        }
        if ( source.getConstructorOptionalToOptional() != null ) {
            constructorOptionalToOptional = source.getConstructorOptionalToOptional();
        }
        else {
            constructorOptionalToOptional = Optional.empty();
        }
        if ( source.getConstructorOptionalSubTypeToNonOptional() != null ) {
            constructorOptionalSubTypeToNonOptional = subTypeOptionalToSubType( source.getConstructorOptionalSubTypeToNonOptional() );
        }
        if ( source.getConstructorNonOptionalSubTypeToOptional() != null ) {
            constructorNonOptionalSubTypeToOptional = subTypeToSubTypeOptional( source.getConstructorNonOptionalSubTypeToOptional() );
        }
        else {
            constructorNonOptionalSubTypeToOptional = Optional.empty();
        }
        if ( source.getConstructorOptionalSubTypeToOptional() != null ) {
            constructorOptionalSubTypeToOptional = subTypeOptionalToSubTypeOptional( source.getConstructorOptionalSubTypeToOptional() );
        }
        else {
            constructorOptionalSubTypeToOptional = Optional.empty();
        }

        Target target = new Target( constructorOptionalToNonOptional, constructorNonOptionalToOptional, constructorOptionalToOptional, constructorOptionalSubTypeToNonOptional, constructorNonOptionalSubTypeToOptional, constructorOptionalSubTypeToOptional );

        if ( source.getOptionalToNonOptional() != null ) {
            target.setOptionalToNonOptional( stringOptionalToString( source.getOptionalToNonOptional() ) );
        }
        if ( source.getNonOptionalToOptional() != null ) {
            target.setNonOptionalToOptional( stringToStringOptional( source.getNonOptionalToOptional() ) );
        }
        else {
            target.setNonOptionalToOptional( Optional.empty() );
        }
        if ( source.getOptionalToOptional() != null ) {
            target.setOptionalToOptional( source.getOptionalToOptional() );
        }
        else {
            target.setOptionalToOptional( Optional.empty() );
        }
        if ( source.getOptionalSubTypeToNonOptional() != null ) {
            target.setOptionalSubTypeToNonOptional( subTypeOptionalToSubType( source.getOptionalSubTypeToNonOptional() ) );
        }
        if ( source.getNonOptionalSubTypeToOptional() != null ) {
            target.setNonOptionalSubTypeToOptional( subTypeToSubTypeOptional( source.getNonOptionalSubTypeToOptional() ) );
        }
        else {
            target.setNonOptionalSubTypeToOptional( Optional.empty() );
        }
        if ( source.getOptionalSubTypeToOptional() != null ) {
            target.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional( source.getOptionalSubTypeToOptional() ) );
        }
        else {
            target.setOptionalSubTypeToOptional( Optional.empty() );
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
        if ( target.getConstructorNonOptionalToOptional() != null ) {
            constructorNonOptionalToOptional = stringOptionalToString( target.getConstructorNonOptionalToOptional() );
        }
        if ( target.getConstructorOptionalToOptional() != null ) {
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
        if ( target.getConstructorNonOptionalSubTypeToOptional() != null ) {
            constructorNonOptionalSubTypeToOptional = subTypeOptionalToSubType1( target.getConstructorNonOptionalSubTypeToOptional() );
        }
        if ( target.getConstructorOptionalSubTypeToOptional() != null ) {
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
        if ( target.getNonOptionalToOptional() != null ) {
            source.setNonOptionalToOptional( stringOptionalToString( target.getNonOptionalToOptional() ) );
        }
        if ( target.getOptionalToOptional() != null ) {
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
        if ( target.getNonOptionalSubTypeToOptional() != null ) {
            source.setNonOptionalSubTypeToOptional( subTypeOptionalToSubType1( target.getNonOptionalSubTypeToOptional() ) );
        }
        if ( target.getOptionalSubTypeToOptional() != null ) {
            source.setOptionalSubTypeToOptional( subTypeOptionalToSubTypeOptional1( target.getOptionalSubTypeToOptional() ) );
        }
        else {
            source.setOptionalSubTypeToOptional( Optional.empty() );
        }

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
