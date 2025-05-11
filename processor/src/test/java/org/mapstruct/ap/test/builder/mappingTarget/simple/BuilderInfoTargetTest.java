/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.mappingTarget.simple;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    MutableTarget.class,
    SimpleMutableSource.class,
    SimpleImmutableTarget.class,
    SimpleBuilderMapper.class
})
public class BuilderInfoTargetTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
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

    @ProcessorTest
    @IssueKey("1752")
    public void testSimpleImmutableBuilderFromNullSource() {
        SimpleImmutableTarget targetObject = SimpleBuilderMapper.INSTANCE.toImmutable(
            null,
            SimpleImmutableTarget.builder().age( 3 ).name( "Bob" )
        );
        assertThat( targetObject ).isNotNull();
        assertThat( targetObject.getAge() ).isEqualTo( 3 );
        assertThat( targetObject.getName() ).isEqualTo( "Bob" );
    }

    @ProcessorTest
    public void testMutableTargetWithBuilder() {
        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 20 );
        source.setFullName( "Filip" );
        MutableTarget target = SimpleBuilderMapper.INSTANCE.toMutableTarget( source );
        assertThat( target.getAge() ).isEqualTo( 20 );
        assertThat( target.getName() ).isEqualTo( "Filip" );
        assertThat( target.getSource() ).isEqualTo( "Builder" );
    }

    @ProcessorTest
    public void testUpdateMutableWithBuilder() {
        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 20 );
        source.setFullName( "Filip" );
        MutableTarget target = new MutableTarget();
        target.setAge( 10 );
        target.setName( "Fil" );

        assertThat( target.getAge() ).isEqualTo( 10 );
        assertThat( target.getName() ).isEqualTo( "Fil" );
        assertThat( target.getSource() ).isEqualTo( "Empty constructor" );

        SimpleBuilderMapper.INSTANCE.updateMutableTarget( source, target );
        assertThat( target.getAge() ).isEqualTo( 20 );
        assertThat( target.getName() ).isEqualTo( "Filip" );
        assertThat( target.getSource() ).isEqualTo( "Empty constructor" );
    }

    @ProcessorTest
    @WithClasses( {
        SimpleImmutableUpdateMapper.class
    } )
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SimpleImmutableUpdateMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 18,
                message = "No target property found for target \"SimpleImmutableTarget\"."),
        })

    public void updatingTargetWithNoSettersShouldNotFail() {

        SimpleMutableSource source = new SimpleMutableSource();
        source.setAge( 10 );

        SimpleImmutableTarget target = SimpleImmutableTarget.builder()
            .age( 20 )
            .build();

        assertThat( target.getAge() ).isEqualTo( 20 );
        SimpleImmutableUpdateMapper.INSTANCE.toImmutable( source, target );
        assertThat( target.getAge() ).isEqualTo( 20 );
    }
}
