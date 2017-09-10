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
package org.mapstruct.ap.test.builder.parentchild;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

@WithClasses({MutableParent.class, MutableChild.class, ImmutableChild.class, ImmutableParent.class})
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class ParentChildBuilderTest {

    @Test
    @WithClasses({ParentChildMapper.class})
    public void testParentChildBuilderHappyPath() {
        final ParentChildMapper mapper = Mappers.getMapper( ParentChildMapper.class );
        final MutableParent parent = new MutableParent();
        parent.setCount( 4 );
        parent.setChildren( new ArrayList<MutableChild>() );
        parent.getChildren().add( new MutableChild( "Phineas" ) );
        parent.getChildren().add( new MutableChild( "Ferb" ) );

        // transform
        final ImmutableParent immutableParent = mapper.toParent( parent );

        assertThat( immutableParent.getCount() ).isEqualTo( 4 );
        assertThat( immutableParent.getChildren() ).hasSize( 2 );
        assertThat( immutableParent.getChildren() )
                .hasSize( 2 )
                .areExactly( 1, hasMatchingName( "Phineas" ) )
                .areExactly( 1, hasMatchingName( "Ferb" ) );
    }

    private Condition<ImmutableChild> hasMatchingName(final String name) {
        return new Condition<ImmutableChild>() {
            @Override
            public boolean matches(ImmutableChild value) {
                return name.equals( value.getBar() );
            }
        };
    }
}
