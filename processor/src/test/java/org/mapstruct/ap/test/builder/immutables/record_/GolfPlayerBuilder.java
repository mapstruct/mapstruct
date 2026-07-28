/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables.record_;

import java.util.ArrayList;
import java.util.List;

/**
 * Hand-crafted stand-in for the builder that the Immutables annotation processor generates for {@link GolfPlayer}.
 * Immutables generates a public no-arg constructor, per-field setters, and a {@code build()} method that enforces
 * mandatory fields via an {@code initBits} bitmask — throwing {@link IllegalStateException} when any required field
 * was never set.
 */
public final class GolfPlayerBuilder {

    private static final long INIT_BIT_NAME = 0x1L;
    private static final long INIT_BIT_AGE = 0x2L;
    private long initBits = 0x3L;

    private String name;
    private Integer handicap;
    private int age;

    public GolfPlayerBuilder() {
    }

    public GolfPlayerBuilder name(String name) {
        this.name = name;
        initBits &= ~INIT_BIT_NAME;
        return this;
    }

    public GolfPlayerBuilder handicap(Integer handicap) {
        this.handicap = handicap;
        return this;
    }

    public GolfPlayerBuilder age(int age) {
        this.age = age;
        initBits &= ~INIT_BIT_AGE;
        return this;
    }

    public GolfPlayer build() {
        checkRequiredAttributes();
        return new GolfPlayer( name, handicap, age );
    }

    private void checkRequiredAttributes() {
        if ( initBits != 0 ) {
            throw new IllegalStateException( formatRequiredAttributesMessage() );
        }
    }

    private String formatRequiredAttributesMessage() {
        List<String> attributes = new ArrayList<>();
        if ( ( initBits & INIT_BIT_NAME ) != 0 ) {
            attributes.add( "name" );
        }
        if ( ( initBits & INIT_BIT_AGE ) != 0 ) {
            attributes.add( "age" );
        }
        return "Cannot build GolfPlayer, some of required attributes are not set " + attributes;
    }
}

