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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Verifies that abstract builders work when mapping to an abstract property type.
 */
@WithClasses({
    AbstractChildTarget.class,
    AbstractParentTarget.class,
    ChildSource.class,
    ImmutableChildTargetImpl.class,
    ImmutableParentTargetImpl.class,
    MutableChildTargetImpl.class,
    MutableParentTargetImpl.class,
    ParentSource.class,
    AbstractTargetMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class AbstractTargetBuilderTest {

    @Test
    public void testAbstractTargetMapper() {
        ParentSource parent = new ParentSource();
        parent.setCount( 4 );
        parent.setNested( new ChildSource( "Phineas" ) );

        // transform
        AbstractParentTarget immutableTarget = AbstractTargetMapper.INSTANCE.toImmutable( parent );
        AbstractParentTarget mutableTarget = AbstractTargetMapper.INSTANCE.toMutable( parent );

        assertThat( mutableTarget.getCount() ).isEqualTo( 4 );
        assertThat( mutableTarget.getNested().getBar() ).isEqualTo( "Phineas" );

        assertThat( immutableTarget.getCount() ).isEqualTo( 4 );
        assertThat( immutableTarget.getNested().getBar() ).isEqualTo( "Phineas" );
    }
}
