/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1242;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that if multiple factory methods are applicable but only one of them has a source parameter, the one with the
 * source param is chosen.
 *
 * @author Andreas Gudian
 */
@IssueKey("1242")
@WithClasses({
    Issue1242Mapper.class,
    SourceA.class,
    SourceB.class,
    TargetA.class,
    TargetB.class,
    TargetFactories.class
})
public class Issue1242Test {
    @ProcessorTest
    public void factoryMethodWithSourceParamIsChosen() {
        SourceA sourceA = new SourceA();
        sourceA.setB( new SourceB() );

        TargetA targetA = new TargetA();
        Mappers.getMapper( Issue1242Mapper.class ).mergeA( sourceA, targetA );

        assertThat( targetA.getB() ).isNotNull();
        assertThat( targetA.getB().getPassedViaConstructor() ).isEqualTo( "created by factory" );

        targetA = Mappers.getMapper( Issue1242Mapper.class ).toTargetA( sourceA );

        assertThat( targetA.getB() ).isNotNull();
        assertThat( targetA.getB().getPassedViaConstructor() ).isEqualTo( "created by factory" );
    }

    @ProcessorTest
    @WithClasses(ErroneousIssue1242MapperMultipleSources.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue1242MapperMultipleSources.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                message = "Ambiguous factory methods found for creating TargetB: " +
                    "TargetB anotherTargetBCreator(SourceB source), " +
                    "TargetB TargetFactories.createTargetB(SourceB source, @TargetType Class<TargetB> clazz), " +
                    "TargetB TargetFactories.createTargetB(@TargetType Class<TargetB> clazz), " +
                    "TargetB TargetFactories.createTargetB(). " +
                    "See https://mapstruct.org/faq/#ambiguous for more info.")
        })
    public void ambiguousMethodErrorForTwoFactoryMethodsWithSourceParam() {
    }
}
