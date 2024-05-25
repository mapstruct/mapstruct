/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.redundant;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-07T18:50:27+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.2 (Azul Systems, Inc.)"
)
public class FooMapperConfiguredImpl implements FooMapperConfigured {

    @Override
    public void updateFoo(FooSource input, FooTarget toUpdate, boolean baz) {

        if ( input != null ) {
            toUpdate.setBar( input.getBar() );
        }
    }

    @Override
    public void updateFoo(FooSource input, FooTarget toUpdate) {

        if ( input != null ) {
            toUpdate.setBar( input.getBar() );
        }
    }
}
