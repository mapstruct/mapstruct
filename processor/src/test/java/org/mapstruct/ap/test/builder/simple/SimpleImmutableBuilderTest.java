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
package org.mapstruct.ap.test.builder.simple;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    SimpleMutablePerson.class,
    SimpleImmutablePerson.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class SimpleImmutableBuilderTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses({ SimpleBuilderMapper.class })
    public void testSimpleImmutableBuilderHappyPath() {
        SimpleBuilderMapper mapper = Mappers.getMapper( SimpleBuilderMapper.class );
        SimpleMutablePerson source = new SimpleMutablePerson();
        source.setAge( 3 );
        source.setFullName( "Bob" );
        source.setChildren( Arrays.asList( "Alice", "Tom" ) );
        source.setAddress( "Plaza 1" );

        SimpleImmutablePerson targetObject = mapper.toImmutable( source );

        assertThat( targetObject.getAge() ).isEqualTo( 3 );
        assertThat( targetObject.getName() ).isEqualTo( "Bob" );
        assertThat( targetObject.getJob() ).isEqualTo( "programmer" );
        assertThat( targetObject.getCity() ).isEqualTo( "Bengalore" );
        assertThat( targetObject.getAddress() ).isEqualTo( "Plaza 1" );
        assertThat( targetObject.getChildren() ).contains( "Alice", "Tom" );
    }

    @Test
    @WithClasses({ ErroneousSimpleBuilderMapper.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = javax.tools.Diagnostic.Kind.ERROR,
            type = ErroneousSimpleBuilderMapper.class,
            line = 34,
            messageRegExp = "Unmapped target property: \"name\"\\."))
    public void testSimpleImmutableBuilderMissingPropertyFailsToCompile() {
    }
}
