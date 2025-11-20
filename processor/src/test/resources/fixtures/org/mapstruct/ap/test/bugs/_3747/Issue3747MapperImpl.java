/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3747;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-03T12:50:02+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.3 (N/A)"
)
public class Issue3747MapperImpl implements Issue3747Mapper {

    @Override
    public Target map(Source source) {

        String value = null;
        if ( source != null ) {
            value = source.getValue();
        }

        Target target = new Target( value );

        return target;
    }
}
