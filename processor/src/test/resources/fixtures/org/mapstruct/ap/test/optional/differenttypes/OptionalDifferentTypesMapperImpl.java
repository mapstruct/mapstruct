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
    date = "2026-01-23T10:02:54+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
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

    protected Optional<Target.SubType> subTypeOptionalToSubTypeOptional(Optional<Source.SubType> optional) {
        if ( optional.isEmpty() ) {
            return Optional.empty();
        }

        String value = null;

        Source.SubType optionalValue = optional.get();

        value = optionalValue.getValue();

        Target.SubType subType = new Target.SubType( value );

        return Optional.of( subType );
    }

    protected Target.SubType subTypeOptionalToSubType(Optional<Source.SubType> optional) {
        if ( optional.isEmpty() ) {
            return null;
        }

        String value = null;

        Source.SubType optionalValue = optional.get();

        value = optionalValue.getValue();

        Target.SubType subType = new Target.SubType( value );

        return subType;
    }

    protected Optional<Target.SubType> subTypeToSubTypeOptional(Source.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        String value = null;

        value = subType.getValue();

        Target.SubType subType1 = new Target.SubType( value );

        return Optional.of( subType1 );
    }
}
