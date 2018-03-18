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
package org.mapstruct.ap.test.builder.abstractGenericTarget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that abstract builders work when mapping to an abstract property type.
 */
@WithClasses({
    Child.class,
    Parent.class,
    ChildSource.class,
    ImmutableChild.class,
    ImmutableParent.class,
    MutableChild.class,
    MutableParent.class,
    ParentSource.class,
    ParentMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class AbstractGenericTargetBuilderTest {

    @Test
    public void testAbstractTargetMapper() {
        ParentSource parent = new ParentSource();
        parent.setCount( 4 );
        parent.setChild( new ChildSource( "Phineas" ) );
        parent.setNonGenericChild( new ChildSource( "Ferb" ) );

        // transform
        Parent immutableParent = ParentMapper.INSTANCE.toImmutable( parent );
        assertThat( immutableParent.getCount() ).isEqualTo( 4 );
        assertThat( immutableParent.getChild().getName() ).isEqualTo( "Phineas" );
        assertThat( immutableParent.getNonGenericChild() )
            .isNotNull()
            .isInstanceOf( ImmutableChild.class );
        assertThat( immutableParent.getNonGenericChild().getName() ).isEqualTo( "Ferb" );

        Parent mutableParent = ParentMapper.INSTANCE.toMutable( parent );

        assertThat( mutableParent.getCount() ).isEqualTo( 4 );
        assertThat( mutableParent.getChild().getName() ).isEqualTo( "Phineas" );
        assertThat( mutableParent.getNonGenericChild() )
            .isNotNull()
            .isInstanceOf( ImmutableChild.class );
        assertThat( mutableParent.getNonGenericChild().getName() ).isEqualTo( "Ferb" );

    }
}
