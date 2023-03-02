/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.differenttypes;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T21:40:58-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class OptionalDifferentTypesMapperImpl implements OptionalDifferentTypesMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Optional<Target.SubType> constructorOptionalToOptional = Optional.empty();
        Target.SubType constructorOptionalToNonOptional = null;
        Optional<Target.SubType> constructorNonOptionalToOptional = Optional.empty();

        constructorOptionalToOptional = subTypeOptionalToSubTypeOptional( source.getConstructorOptionalToOptional() );
        constructorOptionalToNonOptional = subTypeOptionalToSubType( source.getConstructorOptionalToNonOptional() );
        constructorNonOptionalToOptional = subTypeToSubTypeOptional( source.getConstructorNonOptionalToOptional() );

        Target target = new Target( constructorOptionalToOptional, constructorOptionalToNonOptional, constructorNonOptionalToOptional );

        target.setOptionalToOptional( subTypeOptionalToSubTypeOptional( source.getOptionalToOptional() ) );
        target.setOptionalToNonOptional( subTypeOptionalToSubType( source.getOptionalToNonOptional() ) );
        target.setNonOptionalToOptional( subTypeToSubTypeOptional( source.getNonOptionalToOptional() ) );
        target.publicOptionalToOptional = subTypeOptionalToSubTypeOptional( source.publicOptionalToOptional );
        target.publicOptionalToNonOptional = subTypeOptionalToSubType( source.publicOptionalToNonOptional );
        target.publicNonOptionalToOptional = subTypeToSubTypeOptional( source.publicNonOptionalToOptional );

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
        if ( optional == null ) {
            return Optional.empty();
        }

        Optional<Target.SubType> optional1 = optional.map( subType -> subTypeToSubType( subType ) );

        return optional1;
    }

    protected Target.SubType subTypeOptionalToSubType(Optional<Source.SubType> optional) {
        if ( optional == null ) {
            return null;
        }

        Target.SubType subType1 = optional.map( subType -> subTypeToSubType( subType ) ).orElse( null );

        return subType1;
    }

    protected Optional<Target.SubType> subTypeToSubTypeOptional(Source.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        Source.SubType subType1 = subType;
        Optional<Target.SubType> optional = Optional.ofNullable( subTypeToSubType( subType1 ) );

        return optional;
    }
}
