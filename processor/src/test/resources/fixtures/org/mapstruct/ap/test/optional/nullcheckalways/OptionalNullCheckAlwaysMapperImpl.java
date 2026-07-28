/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullcheckalways;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:56:21+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class OptionalNullCheckAlwaysMapperImpl implements OptionalNullCheckAlwaysMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Optional<String> optionalToOptional = source.getOptionalToOptional();
        if ( optionalToOptional.isPresent() ) {
            target.setOptionalToOptional( optionalToOptional );
        }
        Optional<String> optionalToNonOptional = source.getOptionalToNonOptional();
        if ( optionalToNonOptional.isPresent() ) {
            target.setOptionalToNonOptional( optionalToNonOptional.get() );
        }
        String nonOptionalToOptional = source.getNonOptionalToOptional();
        if ( nonOptionalToOptional != null ) {
            target.setNonOptionalToOptional( Optional.of( nonOptionalToOptional ) );
        }

        return target;
    }
}
