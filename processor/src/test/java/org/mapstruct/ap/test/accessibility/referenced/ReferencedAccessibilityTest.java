/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.accessibility.referenced.a.ReferencedMapperDefaultOther;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for different accessibility modifiers
 *
 * @author Sjaak Derksen
 */
@WithClasses( { Source.class, Target.class, ReferencedSource.class, ReferencedTarget.class } )
public class ReferencedAccessibilityTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @IssueKey("206")
    @WithClasses({ SourceTargetMapperPrivate.class, ReferencedMapperPrivate.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperPrivate.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 22,
                message = "Unmapped target property: \"bar\". Mapping from property " +
                    "\"ReferencedSource referencedSource\" to \"ReferencedTarget referencedTarget\".")
        }
    )
    public void shouldNotBeAbleToAccessPrivateMethodInReferenced() {
        generatedSource.addComparisonToFixtureFor( SourceTargetMapperPrivate.class );
    }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperDefaultSame.class, ReferencedMapperDefaultSame.class } )
    public void shouldBeAbleToAccessDefaultMethodInReferencedInSamePackage() { }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperProtected.class, ReferencedMapperProtected.class } )
    public void shouldBeAbleToAccessProtectedMethodInReferencedInSamePackage() { }

    @ProcessorTest
    @IssueKey("206")
    @WithClasses({ SourceTargetMapperDefaultOther.class, ReferencedMapperDefaultOther.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperDefaultOther.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 24,
                message = "Unmapped target property: \"bar\". Mapping " +
                    "from property \"ReferencedSource referencedSource\" to \"ReferencedTarget referencedTarget\".")
        }
    )
    public void shouldNotBeAbleToAccessDefaultMethodInReferencedInOtherPackage() {
        generatedSource.addComparisonToFixtureFor( SourceTargetMapperDefaultOther.class );
    }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { AbstractSourceTargetMapperProtected.class, SourceTargetmapperProtectedBase.class } )
    public void shouldBeAbleToAccessProtectedMethodInBase() { }

    @ProcessorTest
    @IssueKey("206")
    @WithClasses({ AbstractSourceTargetMapperPrivate.class, SourceTargetmapperPrivateBase.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = AbstractSourceTargetMapperPrivate.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                message = "Unmapped target property: \"bar\". Mapping from property " +
                    "\"ReferencedSource referencedSource\" to \"ReferencedTarget referencedTarget\".")
        }
    )
    public void shouldNotBeAbleToAccessPrivateMethodInBase() {
        generatedSource.addComparisonToFixtureFor( AbstractSourceTargetMapperPrivate.class );
    }
}
