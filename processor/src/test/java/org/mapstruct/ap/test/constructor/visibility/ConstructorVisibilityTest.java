/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.visibility;

import org.mapstruct.ap.test.constructor.Default;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2150")
@WithClasses({
    PersonDto.class,
    Default.class,
})
public class ConstructorVisibilityTest {

    @ProcessorTest
    @WithClasses({
        SimpleWithPublicConstructorMapper.class
    })
    public void shouldUseSinglePublicConstructorAlways() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );

        SimpleWithPublicConstructorMapper.Person target =
            SimpleWithPublicConstructorMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 30 );
    }

    @ProcessorTest
    @WithClasses({
        SimpleWithPublicParameterlessConstructorMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SimpleWithPublicParameterlessConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 21,
                message = "No target property found for target " +
                    "\"SimpleWithPublicParameterlessConstructorMapper.Person\"."),
        })

    public void shouldUsePublicParameterConstructorIfPresent() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );

        SimpleWithPublicParameterlessConstructorMapper.Person target =
            SimpleWithPublicParameterlessConstructorMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "From Constructor" );
        assertThat( target.getAge() ).isEqualTo( -1 );
    }

    @ProcessorTest
    @WithClasses({
        SimpleWithPublicParameterlessConstructorAndDefaultAnnotatedMapper.class
    })
    public void shouldUseDefaultAnnotatedConstructorAlways() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );

        SimpleWithPublicParameterlessConstructorAndDefaultAnnotatedMapper.Person target =
            SimpleWithPublicParameterlessConstructorAndDefaultAnnotatedMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 30 );
    }
}
