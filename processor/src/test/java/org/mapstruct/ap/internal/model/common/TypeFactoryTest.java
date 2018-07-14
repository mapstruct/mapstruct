/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TypeFactoryTest {

    @Test
    public void shouldReturnNullIfNoClassNameIsProvided() {
        String result = TypeFactory.trimSimpleClassName( null );

        assertThat( result ).isNull();
    }

    @Test
    public void shouldNotModifyClassNameIfNotAnArray() {
        String className = "SimpleClass";

        String result = TypeFactory.trimSimpleClassName( className );

        assertThat( result ).isEqualTo( className );
    }

    @Test
    public void shouldTrimOneDimensionalArray() {
        String result = TypeFactory.trimSimpleClassName( "SimpleClass[]" );

        assertThat( result ).isEqualTo( "SimpleClass" );
    }

    @Test
    public void shouldTrimTwoDimensionalArray() {
        String result = TypeFactory.trimSimpleClassName( "SimpleClass[][]" );

        assertThat( result ).isEqualTo( "SimpleClass" );
    }

    @Test
    public void shouldTrimMultiDimensionalArray() {
        String result = TypeFactory.trimSimpleClassName( "SimpleClass[][][][][]" );

        assertThat( result ).isEqualTo( "SimpleClass" );
    }
}
