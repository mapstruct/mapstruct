/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.wildcard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
public class WildCardTest {

    @Test
    @WithClasses({
        ExtendsBoundSourceTargetMapper.class,
        ExtendsBoundSource.class,
        Target.class,
        Plan.class,
        Idea.class
    })
    public void shouldGenerateExtendsBoundSourceForgedStreamMethod() {

        ExtendsBoundSource source = new ExtendsBoundSource();

        Target target = ExtendsBoundSourceTargetMapper.STM.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getElements() ).isNull();
        assertThat( target.getListElements() ).isNull();

    }

    @Test
    @WithClasses({
        SourceSuperBoundTargetMapper.class,
        Source.class,
        SuperBoundTarget.class,
        Plan.class,
        Idea.class
    })
    public void shouldGenerateSuperBoundTargetForgedIterableMethod() {

        Source source = new Source();

        SuperBoundTarget target = SourceSuperBoundTargetMapper.STM.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getElements() ).isNull();
        assertThat( target.getListElements() ).isNull();

    }

    @Test
    @WithClasses({ ErroneousIterableSuperBoundSourceMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIterableSuperBoundSourceMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                messageRegExp = "Can't generate mapping method for a wildcard super bound source.")
        }
    )
    public void shouldFailOnSuperBoundSource() {
    }

    @Test
    @WithClasses({ ErroneousIterableExtendsBoundTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIterableExtendsBoundTargetMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                messageRegExp = "Can't generate mapping method for a wildcard extends bound result.")
        }
    )
    public void shouldFailOnExtendsBoundTarget() {
    }

    @Test
    @WithClasses({ ErroneousIterableTypeVarBoundMapperOnMethod.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIterableTypeVarBoundMapperOnMethod.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                messageRegExp = "Can't generate mapping method for a generic type variable target.")
        }
    )
    public void shouldFailOnTypeVarSource() {
    }

    @Test
    @WithClasses({ ErroneousIterableTypeVarBoundMapperOnMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIterableTypeVarBoundMapperOnMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                messageRegExp = "Can't generate mapping method for a generic type variable source.")
        }
    )
    public void shouldFailOnTypeVarTarget() {
    }
}
