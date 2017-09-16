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
package org.mapstruct.ap.test.builder.mappingTarget.simple;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.factory.Mappers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@WithClasses({SimpleMutableSource.class, SimpleImmutableTarget.class})
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class BuilderMappingTargetTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses({SimpleBuilderMapper.class})
    public void testSimpleImmutableBuilderHappyPath() {
        final SimpleBuilderMapper mapper = Mappers.getMapper( SimpleBuilderMapper.class );
        final SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 3 );
        source.setFullName( "Bob" );
        final SimpleImmutableTarget targetObject = mapper.toImmutable( source, SimpleImmutableTarget.builder() );
        assertThat( targetObject.getAge() ).isEqualTo( 3 );
        assertThat( targetObject.getName() ).isEqualTo( "Bob" );
    }
}
