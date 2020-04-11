/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.builder.multiple.build.Process;
import org.mapstruct.ap.test.builder.multiple.builder.Case;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1479")
@WithClasses({
    Process.class,
    Case.class,
    Task.class,
    Source.class
})
public class MultipleBuilderMapperTest {

    @WithClasses({
        ErroneousMoreThanOneBuildMethodMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousMoreThanOneBuildMethodMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "No build method \"build\" found in \"org.mapstruct.ap.test.builder.multiple.build.Process" +
                    ".Builder\" for \"org.mapstruct.ap.test.builder.multiple.build.Process\". Found methods: " +
                    "\"wrongCreate(), create()\". Consider to add @Builder in order to select the correct build method."
            ),
            @Diagnostic(
                type = ErroneousMoreThanOneBuildMethodMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                message = "No build method \"missingBuild\" found in \"org.mapstruct.ap.test.builder.multiple.build" +
                    ".Process.Builder\" for \"org.mapstruct.ap.test.builder.multiple.build.Process\". Found methods: " +
                    "\"wrongCreate(), create()\"."
            )
        })
    @Test
    public void moreThanOneBuildMethod() {
    }

    @WithClasses({
        ErroneousMoreThanOneBuildMethodWithMapperDefinedMappingMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousMoreThanOneBuildMethodWithMapperDefinedMappingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                message =
                    "No build method \"mapperBuild\" found in \"org.mapstruct.ap.test.builder.multiple.build.Process" +
                        ".Builder\" for \"org.mapstruct.ap.test.builder.multiple.build.Process\". Found methods: " +
                        "\"wrongCreate(), create()\"."
            )
        })
    @Test
    public void moreThanOneBuildMethodDefinedOnMapper() {
    }

    @WithClasses({
        BuilderDefinedMapper.class
    })
    @Test
    public void builderMappingDefined() {
        Process map = BuilderDefinedMapper.INSTANCE.map( new Source( "map" ) );
        Process wrongMap = BuilderDefinedMapper.INSTANCE.wrongMap( new Source( "wrongMap" ) );

        assertThat( map.getBuildMethod() ).isEqualTo( "create" );
        assertThat( wrongMap.getBuildMethod() ).isEqualTo( "wrongCreate" );
    }

    @WithClasses({
        BuilderMapperConfig.class,
        BuilderConfigDefinedMapper.class
    })
    @Test
    public void builderMappingMapperConfigDefined() {
        Process map = BuilderConfigDefinedMapper.INSTANCE.map( new Source( "map" ) );
        Process wrongMap = BuilderConfigDefinedMapper.INSTANCE.wrongMap( new Source( "wrongMap" ) );

        assertThat( map.getBuildMethod() ).isEqualTo( "create" );
        assertThat( wrongMap.getBuildMethod() ).isEqualTo( "wrongCreate" );
    }

    @WithClasses({
        TooManyBuilderCreationMethodsMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                type = Case.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 11,
                message = "More than one builder creation method for \"org.mapstruct.ap.test.builder.multiple.builder" +
                    ".Case\". Found methods: \"wrongBuilder(), builder()\". Builder will not be used. Consider " +
                    "implementing a custom BuilderProvider SPI."
            )
        })
    @Test
    public void tooManyBuilderCreationMethods() {
        Case caseTarget = TooManyBuilderCreationMethodsMapper.INSTANCE.map( new Source( "test" ) );

        assertThat( caseTarget ).isNotNull();
        assertThat( caseTarget.getName() ).isEqualTo( "test" );
        assertThat( caseTarget.getBuilderCreationMethod() ).isNull();
        assertThat( caseTarget.getBuildMethod() ).isEqualTo( "constructor" );
    }

    @WithClasses( {
        DefaultBuildMethodMapper.class
    } )
    @Test
    public void defaultBuildMethod() {
        Task task = DefaultBuildMethodMapper.INSTANCE.map( new Source( "test" ) );

        assertThat( task ).isNotNull();
        assertThat( task.getName() ).isEqualTo( "test" );
        assertThat( task.getBuilderCreationMethod() ).isEqualTo( "builder" );
        assertThat( task.getBuildMethod() ).isEqualTo( "build" );
    }
}
