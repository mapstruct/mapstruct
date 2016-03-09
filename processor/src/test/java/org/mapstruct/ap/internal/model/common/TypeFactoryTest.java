/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.model.common;

import static org.fest.assertions.Assertions.assertThat;

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
