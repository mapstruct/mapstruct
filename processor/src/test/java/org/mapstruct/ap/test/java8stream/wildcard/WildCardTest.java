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
                line = 34,
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
                line = 34,
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
                line = 34,
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
                line = 34,
                messageRegExp = "Can't generate mapping method for a generic type variable source.")
        }
    )
    public void shouldFailOnTypeVarTarget() {
    }
}
