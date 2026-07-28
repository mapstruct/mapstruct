/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4077;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "0000-00-00T00:00:00+0000",
    comments = "version: , compiler: javac, environment: Java 21"
)
public class Issue4077MapperImpl implements Issue4077Mapper {

    @Override
    public Target map(Source source) {

        Target.Nested nested = null;

        if ( source.getNested() != null ) {
            nested = mapNested( source.getNested() );
        }

        Target target = new Target( nested );

        return target;
    }

    @Override
    public Target.Nested mapNested(Source.Nested source) {

        String foo = null;
        Integer bar = null;

        foo = source.getFoo();
        bar = source.getBar();

        Target.Nested nested = new Target.Nested( foo, bar );

        return nested;
    }
}
