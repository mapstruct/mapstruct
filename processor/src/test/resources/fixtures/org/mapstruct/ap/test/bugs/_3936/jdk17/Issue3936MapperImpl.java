/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3936.jdk17;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-30T00:00:00+0000",
    comments = "version: , compiler: javac, environment: Java 21"
)
public class Issue3936MapperImpl implements Issue3936Mapper {

    @Override
    public Issue3936Mapper.To map(Issue3936Mapper.From from) {
        if ( from == null ) {
            return null;
        }

        if (from instanceof Issue3936Mapper.From.From1) {
            return from1ToTo( (Issue3936Mapper.From.From1) from );
        }
        else if (from instanceof Issue3936Mapper.From.From2) {
            return from2ToTo( (Issue3936Mapper.From.From2) from );
        }
        else {
            throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for " + from.getClass());
        }
    }

    protected Issue3936Mapper.To from1ToTo(Issue3936Mapper.From.From1 from1) {
        if ( from1 == null ) {
            return null;
        }

        Integer a = null;

        a = from1.a();

        Issue3936Mapper.To to = new Issue3936Mapper.To( a );

        return to;
    }

    protected Issue3936Mapper.To from2ToTo(Issue3936Mapper.From.From2 from2) {
        if ( from2 == null ) {
            return null;
        }

        Integer a = null;

        a = from2.a();

        Issue3936Mapper.To to = new Issue3936Mapper.To( a );

        return to;
    }
}
