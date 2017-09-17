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
package org.mapstruct.ap.test.builder.abstractBuilder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * This test is for abstract builders, where some of the target properties are written by the abstract
 * builder class, and some of the properties are written by the concrete builder implementation.
 */
@WithClasses({ Target.class, AbstractTargetBuilder.class, ImmutableTarget.class, Source.class })
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class AbstractBuilderTest {

    /**
     * This test verifies that:
     * WHEN - a mapping method's return type is an immutable/built target, AND
     *      - the mapped properties are split between the abstract and concrete builder, AND
     * THEN
     *      - the builder is "discovered"
     *      - all values are mapped
     *      - all values are set properly
     */
    @Test
    @WithClasses(ImmutableTargetMapper.class)
    public void testThatAbstractBuilderMapsAllProperties() {
        final ImmutableTargetMapper mapper = Mappers.getMapper( ImmutableTargetMapper.class );
        final ImmutableTarget sourceOne = mapper.fromThingTwo( new Source( "foo", 31 ) );

        assertThat( sourceOne.getBar() ).isEqualTo( 31 );
        assertThat( sourceOne.getFoo() ).isEqualTo( "foo" );
    }

    @Test
    @WithClasses(ImmutableTargetMapper.class)
    public void testThatAbstractBuilderReverseMapsAllProperties() {
        final ImmutableTargetMapper mapper = Mappers.getMapper( ImmutableTargetMapper.class );
        final Source sourceOne = mapper.fromImmutable( ImmutableTarget.builder()
            .bar( 31 )
            .foo( "foo" )
            .build() );

        assertThat( sourceOne.getBar() ).isEqualTo( 31 );
        assertThat( sourceOne.getFoo() ).isEqualTo( "foo" );
    }
}
