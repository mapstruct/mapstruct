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
 * This set of tests verifies that an abstract builder (where the builder itself is subclassed and/or uses type
 * parameters) works properly.  It uses a mapped target that has two properties, and then an abstract builder
 * where one of the properties is written in the abstract base builder, and the other is written in the
 * concrete subclass.
 */
@WithClasses({AbstractThing.class, AbstractThingBuilder.class, ThingOne.class, ThingTwo.class})
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class AbstractBuilderTest {

    @Test
    @WithClasses( ThingOneMapper.class )
    public void testAbstractBuilderHappyPath() {
        final ThingOneMapper mapper = Mappers.getMapper( ThingOneMapper.class );
        final ThingOne thingOne = mapper.fromThingTwo( new ThingTwo("foo", 31) );

        assertThat( thingOne.getBar() ).isEqualTo( 31 );
        assertThat( thingOne.getFoo() ).isEqualTo( "foo" );
    }
}
