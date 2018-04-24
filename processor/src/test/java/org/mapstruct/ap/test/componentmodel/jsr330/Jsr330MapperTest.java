/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.componentmodel.jsr330;

import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class Jsr330MapperTest {
    @Test
    @WithClasses( Jsr330UnconfiguredMapper.class )
    public void testWithoutConfiguration() {
        assertThatMapperHasName( Jsr330UnconfiguredMapper.INSTANCE, "" );
    }

    @Test
    @WithClasses( Jsr330MapperComponentModelMapper.class )
    public void testWithDeprecatedMapperComponentTypeValue() {
        assertThatMapperHasName( Jsr330MapperComponentModelMapper.INSTANCE, "" );
    }

    @Test
    @WithClasses( Jsr330NamedMapper.class )
    public void testWithNameConfiguration() {
        assertThatMapperHasName( Jsr330NamedMapper.INSTANCE, "jsr330Mapper" );
    }

    @Test
    @WithClasses( Jsr330NamedDecoratorMapper.class )
    public void testDecoratedWithNameConfiguration() {
        assertThatMapperHasName( Jsr330NamedDecoratorMapper.INSTANCE, "jsr330Mapper" );
    }

    private void assertThatMapperHasName(Object mapper, String expectedName) {
        assertThat( mapper ).isNotNull();

        Named annotation = mapper.getClass().getAnnotation( Named.class );

        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( expectedName );
    }

}
