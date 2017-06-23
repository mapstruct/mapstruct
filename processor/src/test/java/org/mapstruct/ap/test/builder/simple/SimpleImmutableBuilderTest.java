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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.factory.Mappers;


@WithClasses({ SimpleMutableSource.class, SimpleImmutableTarget.class  })
@RunWith(AnnotationProcessorTestRunner.class)
@Category(BuilderTests.class)
public class SimpleImmutableBuilderTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses( {SimpleBuilderMapper.class} )
    public void testSimpleImmutableBuilder_HappyPath() {
        final SimpleBuilderMapper mapper = Mappers.getMapper( SimpleBuilderMapper.class );
        final SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 3 );
        source.setFullName( "Bob" );
        final SimpleImmutableTarget targetObject = mapper.toImmutable( source );
        assertThat( targetObject.getAge() ).isEqualTo( 3 );
        assertThat( targetObject.getName() ).isEqualTo( "Bob");
    }

    @Test
    @WithClasses( {SimpleInvalidBuilderMapper.class} )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
            diagnostics = @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = SimpleInvalidBuilderMapper.class,
                    line = 27,
                    messageRegExp = "Unmapped target property: \"name\"."))
    public void testSimpleImmutableBuilder_MissingProperty_FailsToCompile() {
    }
}
