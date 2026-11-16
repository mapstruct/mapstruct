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
    date = "2026-01-23T10:02:54+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
public class OptionalNullCheckAlwaysMapperImpl implements OptionalNullCheckAlwaysMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getOptionalToOptional().isPresent() ) {
            target.setOptionalToOptional( source.getOptionalToOptional() );
        }
        if ( source.getOptionalToNonOptional().isPresent() ) {
            target.setOptionalToNonOptional( source.getOptionalToNonOptional().get() );
        }
        if ( source.getNonOptionalToOptional() != null ) {
            target.setNonOptionalToOptional( Optional.of( source.getNonOptionalToOptional() ) );
        }

        return target;
    }
}
