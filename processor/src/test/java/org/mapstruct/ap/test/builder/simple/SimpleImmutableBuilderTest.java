/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
            line = 21,
            message = "Unmapped target property: \"name\"."))
    public void testSimpleImmutableBuilderMissingPropertyFailsToCompile() {
    }
}
