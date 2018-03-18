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
package org.mapstruct.ap.test.factories.targettype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Remo Meier
 */
@WithClasses( { Foo9Base.class, Foo9Child.class, Bar9Base.class, Bar9Child.class, Bar9Factory.class,
    TargetTypeFactoryTestMapper.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class ProductTypeFactoryTest {

    @Test
    public void shouldUseFactoryTwoCreateBaseClassDueToTargetType() {
        Foo9Base foo9 = new Foo9Base();
        foo9.setProp( "foo9" );

        Bar9Base bar9 = TargetTypeFactoryTestMapper.INSTANCE.foo9BaseToBar9Base( foo9 );

        assertThat( bar9 ).isNotNull();
        assertThat( bar9.getProp() ).isEqualTo( "foo9" );
        assertThat( bar9.getSomeTypeProp() ).isEqualTo( "FOO9" );
    }

    @Test
    public void shouldUseFactoryTwoCreateChildClassDueToTargetType() {
        Foo9Child foo9 = new Foo9Child();
        foo9.setProp( "foo9" );
        foo9.setChildProp( "foo9Child" );

        Bar9Child bar9 = TargetTypeFactoryTestMapper.INSTANCE.foo9ChildToBar9Child( foo9 );

        assertThat( bar9 ).isNotNull();
        assertThat( bar9.getProp() ).isEqualTo( "foo9" );
        assertThat( bar9.getChildProp() ).isEqualTo( "foo9Child" );
        assertThat( bar9.getSomeTypeProp() ).isEqualTo( "FOO9" );
    }
}
