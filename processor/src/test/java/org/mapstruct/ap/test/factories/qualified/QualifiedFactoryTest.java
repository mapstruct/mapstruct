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
package org.mapstruct.ap.test.factories.qualified;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Remo Meier
 */
@WithClasses( { Foo10.class, Bar10.class, TestQualifier.class, Bar10Factory.class, QualifiedFactoryTestMapper.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class QualifiedFactoryTest {

    @Test
    public void shouldUseFactoryWithoutQualifier() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Lower( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "foo10" );
    }

    @Test
    public void shouldUseFactoryWithQualifier() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Lower( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "foo10" );
    }

    @Test
    public void shouldUseFactoryWithQualifierName() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Camel( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "Foo10" );
    }
}
