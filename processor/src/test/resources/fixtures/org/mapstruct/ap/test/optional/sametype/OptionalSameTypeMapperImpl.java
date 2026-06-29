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
    date = "2026-06-05T14:56:21+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
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
        Optional<String> constructorOptionalToNonOptional1 = source.getConstructorOptionalToNonOptional();
        if ( constructorOptionalToNonOptional1.isPresent() ) {
            constructorOptionalToNonOptional = constructorOptionalToNonOptional1.get();
        }
        String constructorNonOptionalToOptional1 = source.getConstructorNonOptionalToOptional();
        if ( constructorNonOptionalToOptional1 != null ) {
            constructorNonOptionalToOptional = Optional.of( constructorNonOptionalToOptional1 );
        }

        Target target = new Target( constructorOptionalToOptional, constructorOptionalToNonOptional, constructorNonOptionalToOptional );

        target.setOptionalToOptional( source.getOptionalToOptional() );
        Optional<String> optionalToNonOptional = source.getOptionalToNonOptional();
        if ( optionalToNonOptional.isPresent() ) {
            target.setOptionalToNonOptional( optionalToNonOptional.get() );
        }
        String nonOptionalToOptional = source.getNonOptionalToOptional();
        if ( nonOptionalToOptional != null ) {
            target.setNonOptionalToOptional( Optional.of( nonOptionalToOptional ) );
        }
        target.publicOptionalToOptional = source.publicOptionalToOptional;
        Optional<String> publicOptionalToNonOptional = source.publicOptionalToNonOptional;
        if ( publicOptionalToNonOptional.isPresent() ) {
            target.publicOptionalToNonOptional = publicOptionalToNonOptional.get();
        }
        String publicNonOptionalToOptional = source.publicNonOptionalToOptional;
        if ( publicNonOptionalToOptional != null ) {
            target.publicNonOptionalToOptional = Optional.of( publicNonOptionalToOptional );
        }

        return target;
    }
}
