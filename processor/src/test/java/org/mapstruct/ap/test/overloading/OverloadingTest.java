/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overloading;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import javax.tools.Diagnostic.Kind;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@WithClasses( { SourceTargetMapper.class, Source.class, Target.class, SourceMappingTargetMapper.class,
    MappingTarget.class } )
public class OverloadingTest {

    @ProcessorTest
    public void testShouldGenerateCorrectMapperImplementation() {
        long updatedOn = 1059811320000L;
        Target result = SourceTargetMapper.INSTANCE.sourceToTarget( new Source( new Date( updatedOn ) ) );
        assertThat( result.getUpdatedOn() ).isEqualTo( updatedOn );
    }

    @ProcessorTest
    public void testShouldGenerateMappingMapperImplementation() {
        long updatedOn = 1059822320000L;
        MappingTarget result = SourceMappingTargetMapper.INSTANCE.sourceToTarget( new Source( new Date( updatedOn ) ) );
        assertThat( result.getUpdatedOnTarget() ).isEqualTo( updatedOn );
    }

    @ProcessorTest
    @WithClasses( { SourceIncompatibleTargetMapper.class, IncompatibleTarget.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceIncompatibleTargetMapper.class,
                kind = Kind.ERROR,
                line = 17,
                message =  "Can't map property \"Date updatedOn\" to \"Map<Integer,Void> updatedOn\". "
                    + "Consider to declare/implement a mapping method: \"Map<Integer,Void> map(Date value)\".")
        }
    )
    public void testShouldntMapWhenNotMatching() {
    }

}
