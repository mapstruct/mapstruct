/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.sametype;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-23T10:02:54+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
public class OptionalSameTypeMapperImpl implements OptionalSameTypeMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Optional<String> constructorOptionalToOptional = Optional.empty();
        String constructorOptionalToNonOptional = null;
        Optional<String> constructorNonOptionalToOptional = Optional.empty();

        constructorOptionalToOptional = source.getConstructorOptionalToOptional();
        if ( source.getConstructorOptionalToNonOptional().isPresent() ) {
            constructorOptionalToNonOptional = source.getConstructorOptionalToNonOptional().get();
        }
        if ( source.getConstructorNonOptionalToOptional() != null ) {
            constructorNonOptionalToOptional = Optional.of( source.getConstructorNonOptionalToOptional() );
        }

        Target target = new Target( constructorOptionalToOptional, constructorOptionalToNonOptional, constructorNonOptionalToOptional );

        target.setOptionalToOptional( source.getOptionalToOptional() );
        if ( source.getOptionalToNonOptional().isPresent() ) {
            target.setOptionalToNonOptional( source.getOptionalToNonOptional().get() );
        }
        if ( source.getNonOptionalToOptional() != null ) {
            target.setNonOptionalToOptional( Optional.of( source.getNonOptionalToOptional() ) );
        }
        target.publicOptionalToOptional = source.publicOptionalToOptional;
        if ( source.publicOptionalToNonOptional.isPresent() ) {
            target.publicOptionalToNonOptional = source.publicOptionalToNonOptional.get();
        }
        if ( source.publicNonOptionalToOptional != null ) {
            target.publicNonOptionalToOptional = Optional.of( source.publicNonOptionalToOptional );
        }

        return target;
    }
}
