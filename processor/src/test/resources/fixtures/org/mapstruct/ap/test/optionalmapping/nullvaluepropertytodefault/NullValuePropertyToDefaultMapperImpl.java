/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nullvaluepropertytodefault;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T21:40:59-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class NullValuePropertyToDefaultMapperImpl implements NullValuePropertyToDefaultMapper {

    @Override
    public void mapTarget(Source source, Target target) {
        if ( source == null ) {
            return;
        }

        if ( source.getOptionalToOptional() != null ) {
            target.setOptionalToOptional( subTypeOptionalToSubTypeOptional( source.getOptionalToOptional() ) );
        }
        else {
            target.setOptionalToOptional( Optional.empty() );
        }
        if ( source.getNonOptionalToOptional() != null ) {
            target.setNonOptionalToOptional( subTypeToSubTypeOptional( source.getNonOptionalToOptional() ) );
        }
        else {
            target.setNonOptionalToOptional( Optional.empty() );
        }
        if ( source.publicOptionalToOptional != null ) {
            target.publicOptionalToOptional = subTypeOptionalToSubTypeOptional( source.publicOptionalToOptional );
        }
        else {
            target.publicOptionalToOptional = Optional.empty();
        }
        if ( source.publicNonOptionalToOptional != null ) {
            target.publicNonOptionalToOptional = subTypeToSubTypeOptional( source.publicNonOptionalToOptional );
        }
        else {
            target.publicNonOptionalToOptional = Optional.empty();
        }
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

    protected Optional<Target.SubType> subTypeToSubTypeOptional(Source.SubType subType) {
        if ( subType == null ) {
            return Optional.empty();
        }

        Source.SubType subType1 = subType;
        Optional<Target.SubType> optional = Optional.ofNullable( subTypeToSubType( subType1 ) );

        return optional;
    }
}
