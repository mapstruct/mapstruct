/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.beforeafter;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T22:01:31-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class OptionalBeforeAfterMapperImpl implements OptionalBeforeAfterMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Optional<Target.SubType> deepOptionalToOptional = null;
        Target.SubType deepOptionalToNonOptional = null;
        Optional<Target.SubType> deepNonOptionalToOptional = null;
        Optional<String> shallowOptionalToOptional = null;
        String shallowOptionalToNonOptional = null;
        Optional<String> shallowNonOptionalToOptional = null;

        deepOptionalToOptional = subTypeOptionalToSubTypeOptional( source.getDeepOptionalToOptional() );
        deepOptionalToNonOptional = subTypeOptionalToSubType( source.getDeepOptionalToNonOptional() );
        deepNonOptionalToOptional = subTypeToSubTypeOptional( source.getDeepNonOptionalToOptional() );
        shallowOptionalToOptional = source.getShallowOptionalToOptional();
        shallowOptionalToNonOptional = stringOptionalToString( source.getShallowOptionalToNonOptional() );
        shallowNonOptionalToOptional = stringToStringOptional( source.getShallowNonOptionalToOptional() );

        Target target = new Target( deepOptionalToOptional, deepOptionalToNonOptional, deepNonOptionalToOptional, shallowOptionalToOptional, shallowOptionalToNonOptional, shallowNonOptionalToOptional );

        return target;
    }

    protected Target.SubType subTypeToSubType(Source.SubType subType) {
        if ( subType == null ) {
            return null;
        }

        String value = null;

        value = subType.getValue();

        Target.SubType subType1 = new Target.SubType( value );

        return subType1;
    }

    protected Optional<Target.SubType> subTypeOptionalToSubTypeOptional(Optional<Source.SubType> optional) {
        beforeDeepOptionalSourceWithNoTargetType( optional );

        if ( optional == null ) {
            return Optional.empty();
        }

        Optional<Target.SubType> optional1 = optional.map( subType -> subTypeToSubType( subType ) );

        afterDeepOptionalSourceWithNoTarget( optional );
        afterDeepOptionalSourceWithOptionalTarget( optional1, optional );

        return optional1;
    }

    protected Target.SubType subTypeOptionalToSubType(Optional<Source.SubType> optional) {
        beforeDeepOptionalSourceWithNoTargetType( optional );
        beforeDeepOptionalSourceWithNonOptionalTargetType( Target.SubType.class, optional );

        if ( optional == null ) {
            return null;
        }

        Target.SubType subType1 = optional.map( subType -> subTypeToSubType( subType ) ).orElse( null );

        afterDeepOptionalSourceWithNoTarget( optional );
        afterDeepOptionalSourceWithNonOptionalTarget( subType1, optional );

        return subType1;
    }

    protected Optional<Target.SubType> subTypeToSubTypeOptional(Source.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        Source.SubType subType1 = subType;
        Optional<Target.SubType> optional = Optional.ofNullable( subTypeToSubType( subType1 ) );

        afterDeepNonOptionalSourceOptionalTarget( optional, subType );

        return optional;
    }

    protected String stringOptionalToString(Optional<String> optional) {
        beforeShallowOptionalSourceWithNoTargetType( optional );
        beforeShallowOptionalSourceWithNonOptionalTargetType( String.class, optional );

        if ( optional == null ) {
            return null;
        }

        String string1 = optional.map( string -> string ).orElse( null );

        afterShallowOptionalSourceWithNoTarget( optional );
        afterShallowOptionalSourceWithNonOptionalTarget( string1, optional );

        return string1;
    }

    protected Optional<String> stringToStringOptional(String string) {
        if ( string == null ) {
            return Optional.empty();
        }

        String string1 = string;
        Optional<String> optional = Optional.ofNullable( string1 );

        afterShallowNonOptionalSourceOptionalTarget( optional, string );

        return optional;
    }
}
