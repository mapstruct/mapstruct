/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-07T10:33:01+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 17.0.3 (Eclipse Adoptium)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setToOptionalProp( Optional.ofNullable( source.getToOptionalProp() ) );
        if ( source.getFromOptionalProp().isPresent() ) {
            target.setFromOptionalProp( source.getFromOptionalProp().get() );
        }
        target.setOptional( source.getOptional() );
        target.toOptionalPubProp = Optional.ofNullable( source.toOptionalPubProp );
        if ( source.fromOptionalPubProp.isPresent() ) {
            target.fromOptionalPubProp = source.fromOptionalPubProp.get();
        }

        return target;
    }
}
