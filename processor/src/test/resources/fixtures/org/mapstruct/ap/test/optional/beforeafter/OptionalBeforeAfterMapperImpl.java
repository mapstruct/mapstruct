/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.beforeafter;

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

        Optional<Target.SubType> deepOptionalToOptional = Optional.empty();
        Target.SubType deepOptionalToNonOptional = null;
        Optional<Target.SubType> deepNonOptionalToOptional = Optional.empty();
        Optional<String> shallowOptionalToOptional = Optional.empty();
        String shallowOptionalToNonOptional = null;
        Optional<String> shallowNonOptionalToOptional = Optional.empty();

        deepOptionalToOptional = subTypeOptionalToSubTypeOptional( source.getDeepOptionalToOptional() );
        deepOptionalToNonOptional = subTypeOptionalToSubType( source.getDeepOptionalToNonOptional() );
        deepNonOptionalToOptional = subTypeToSubTypeOptional( source.getDeepNonOptionalToOptional() );
        shallowOptionalToOptional = source.getShallowOptionalToOptional();
        if ( source.getShallowOptionalToNonOptional().isPresent() ) {
            shallowOptionalToNonOptional = source.getShallowOptionalToNonOptional().get();
        }
        if ( source.getShallowNonOptionalToOptional() != null ) {
            shallowNonOptionalToOptional = Optional.of( source.getShallowNonOptionalToOptional() );
        }

        Target target = new Target( deepOptionalToOptional, deepOptionalToNonOptional, deepNonOptionalToOptional, shallowOptionalToOptional, shallowOptionalToNonOptional, shallowNonOptionalToOptional );

        return target;
    }

    protected Optional<Target.SubType> subTypeOptionalToSubTypeOptional(Optional<Source.SubType> optional) {
        beforeDeepOptionalSourceWithNoTargetType( optional );
        beforeDeepOptionalSourceWithNonOptionalTargetType( Target.SubType.class, optional );

        if ( optional.isEmpty() ) {
            return Optional.empty();
        }

        String value = null;

        Source.SubType optionalValue = optional.get();

        value = optionalValue.getValue();

        Target.SubType subType = new Target.SubType( value );

        afterDeepOptionalSourceWithNoTarget( optional );
        afterDeepOptionalSourceWithNonOptionalTarget( subType, optional );

        return Optional.of( subType );
    }

    protected Target.SubType subTypeOptionalToSubType(Optional<Source.SubType> optional) {
        beforeDeepOptionalSourceWithNoTargetType( optional );
        beforeDeepOptionalSourceWithNonOptionalTargetType( Target.SubType.class, optional );

        if ( optional.isEmpty() ) {
            return null;
        }

        String value = null;

        Source.SubType optionalValue = optional.get();

        value = optionalValue.getValue();

        Target.SubType subType = new Target.SubType( value );

        afterDeepOptionalSourceWithNoTarget( optional );
        afterDeepOptionalSourceWithNonOptionalTarget( subType, optional );

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
