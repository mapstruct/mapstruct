package org.mapstruct.ap.test.optionalmapping.nullvaluetodefault;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T21:40:57-0500",
    comments = "version: , compiler: javac, environment: Java 11.0.19 (Homebrew)"
)
public class NullValueToDefaultMapperImpl implements NullValueToDefaultMapper {

    @Override
    public Target toTarget(Source source) {

        Optional<Target.SubType> constructorOptionalToOptional = null;
        Target.SubType constructorOptionalToNonOptional = null;
        Optional<Target.SubType> constructorNonOptionalToOptional = null;
        if ( source != null ) {
            constructorOptionalToOptional = subTypeOptionalToSubTypeOptional( source.getConstructorOptionalToOptional() );
            constructorOptionalToNonOptional = subTypeOptionalToSubType( source.getConstructorOptionalToNonOptional() );
            constructorNonOptionalToOptional = subTypeToSubTypeOptional( source.getConstructorNonOptionalToOptional() );
        }

        Target target = new Target( constructorOptionalToOptional, constructorOptionalToNonOptional, constructorNonOptionalToOptional );

        if ( source != null ) {
            target.setOptionalToOptional( subTypeOptionalToSubTypeOptional( source.getOptionalToOptional() ) );
            target.setOptionalToNonOptional( subTypeOptionalToSubType( source.getOptionalToNonOptional() ) );
            target.setNonOptionalToOptional( subTypeToSubTypeOptional( source.getNonOptionalToOptional() ) );
            target.publicOptionalToOptional = subTypeOptionalToSubTypeOptional( source.publicOptionalToOptional );
            target.publicOptionalToNonOptional = subTypeOptionalToSubType( source.publicOptionalToNonOptional );
            target.publicNonOptionalToOptional = subTypeToSubTypeOptional( source.publicNonOptionalToOptional );
        }

        return target;
    }

    protected Target.SubType subTypeToSubType(Source.SubType subType) {

        String value = null;
        if ( subType != null ) {
            value = subType.getValue();
        }

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
