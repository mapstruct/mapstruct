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
public class FooMapperImpl implements FooMapper {

    @Override
    public void updateFoo(FooSource input, FooTarget toUpdate, boolean baz) {
        if ( input == null ) {
            return;
        }

        toUpdate.setBar( input.getBar() );
    }

    @Override
    public void updateFoo(FooSource input, FooTarget toUpdate, boolean baz, int bay) {
        if ( input == null ) {
            return;
        }

        toUpdate.setBar( input.getBar() );
    }

    @Override
    public FooTarget getUpdatedFooTarget(FooSource input, FooTarget toUpdate, boolean baz) {
        if ( input == null ) {
            return toUpdate;
        }

        toUpdate.setBar( input.getBar() );

        return toUpdate;
    }

    @Override
    public FooTarget map(FooSourceNested input, FooTarget toUpdate, boolean baz) {
        if ( input == null ) {
            return toUpdate;
        }

        toUpdate.setBar( inputNestedBar( input ) );

        return toUpdate;
    }

    private String inputNestedBar(FooSourceNested fooSourceNested) {
        FooSource nested = fooSourceNested.getNested();
        if ( nested == null ) {
            return null;
        }
        return nested.getBar();
    }
}
