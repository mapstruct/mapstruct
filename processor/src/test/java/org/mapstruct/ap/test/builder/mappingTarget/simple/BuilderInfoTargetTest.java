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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@WithClasses({
    SimpleMutableSource.class,
    SimpleImmutableTarget.class,
    SimpleBuilderMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class BuilderInfoTargetTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    public void testSimpleImmutableBuilderHappyPath() {
        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 3 );
        source.setFullName( "Bob" );
        SimpleImmutableTarget targetObject = SimpleBuilderMapper.INSTANCE.toImmutable(
            source,
            SimpleImmutableTarget.builder()
        );
        assertThat( targetObject.getAge() ).isEqualTo( 3 );
        assertThat( targetObject.getName() ).isEqualTo( "Bob" );
    }

    @WithClasses(ErroneousSimpleBuilderMapper.class)
    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSimpleBuilderMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "^Can't generate mapping method when @MappingTarget is supposed to be immutable "
                + "\\(has a builder\\)\\.$")
        })
    public void shouldFailCannotModifyImmutable() {

    }
}
