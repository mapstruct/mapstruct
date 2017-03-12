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
package org.mapstruct.ap.test.accessibility.referenced;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.accessibility.referenced.a.ReferencedMapperDefaultOther;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for different accessibility modifiers
 *
 * @author Sjaak Derksen
 */
@WithClasses( { Source.class, Target.class, ReferencedSource.class, ReferencedTarget.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class ReferencedAccessibilityTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @IssueKey("206")
    @WithClasses({ SourceTargetMapperPrivate.class, ReferencedMapperPrivate.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperPrivate.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 35,
                messageRegExp = "Unmapped target property: \"bar\"\\. Mapping from property \"org\\.mapstruct\\.ap\\" +
                    ".test\\.accessibility\\.referenced\\.ReferencedSource referencedSource\" to \"org\\.mapstruct\\" +
                    ".ap\\.test\\.accessibility\\.referenced\\.ReferencedTarget referencedTarget\"")
        }
    )
    public void shouldNotBeAbleToAccessPrivateMethodInReferenced() throws Exception {
        generatedSource.addComparisonToFixtureFor( SourceTargetMapperPrivate.class );
    }

    @Test
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperDefaultSame.class, ReferencedMapperDefaultSame.class } )
    public void shouldBeAbleToAccessDefaultMethodInReferencedInSamePackage() throws Exception { }

    @Test
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperProtected.class, ReferencedMapperProtected.class } )
    public void shouldBeAbleToAccessProtectedMethodInReferencedInSamePackage() throws Exception { }

    @Test
    @IssueKey("206")
    @WithClasses({ SourceTargetMapperDefaultOther.class, ReferencedMapperDefaultOther.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperDefaultOther.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 37,
                messageRegExp = "Unmapped target property: \"bar\"\\. Mapping from property \"org\\.mapstruct\\.ap\\" +
                    ".test\\.accessibility\\.referenced\\.ReferencedSource referencedSource\" to \"org\\.mapstruct\\" +
                    ".ap\\.test\\.accessibility\\.referenced\\.ReferencedTarget referencedTarget\"")
        }
    )
    public void shouldNotBeAbleToAccessDefaultMethodInReferencedInOtherPackage() throws Exception {
        generatedSource.addComparisonToFixtureFor( SourceTargetMapperDefaultOther.class );
    }

    @Test
    @IssueKey( "206" )
    @WithClasses( { AbstractSourceTargetMapperProtected.class, SourceTargetmapperProtectedBase.class } )
    public void shouldBeAbleToAccessProtectedMethodInBase() throws Exception { }

    @Test
    @IssueKey("206")
    @WithClasses({ AbstractSourceTargetMapperPrivate.class, SourceTargetmapperPrivateBase.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = AbstractSourceTargetMapperPrivate.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 36,
                messageRegExp = "Unmapped target property: \"bar\"\\. Mapping from property \"org\\.mapstruct\\.ap\\" +
                    ".test\\.accessibility\\.referenced\\.ReferencedSource referencedSource\" to \"org\\.mapstruct\\" +
                    ".ap\\.test\\.accessibility\\.referenced\\.ReferencedTarget referencedTarget\"")
        }
    )
    public void shouldNotBeAbleToAccessPrivateMethodInBase() throws Exception {
        generatedSource.addComparisonToFixtureFor( AbstractSourceTargetMapperPrivate.class );
    }
}
