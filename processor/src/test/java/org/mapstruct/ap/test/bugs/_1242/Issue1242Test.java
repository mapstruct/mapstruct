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
package org.mapstruct.ap.test.bugs._1242;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * Tests that if multiple factory methods are applicable but only one of them has a source parameter, the one with the
 * source param is chosen.
 *
 * @author Andreas Gudian
 */
@IssueKey("1242")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue1242Mapper.class,
    SourceA.class,
    SourceB.class,
    TargetA.class,
    TargetB.class,
    TargetFactories.class
})
public class Issue1242Test {
    @Test
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

    @Test
    @WithClasses(ErroneousIssue1242MapperMultipleSources.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue1242MapperMultipleSources.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 33,
                messageRegExp = "Ambiguous factory methods found for creating .*TargetB:"
                    + " .*TargetB anotherTargetBCreator\\(.*SourceB source\\),"
                    + " .*TargetB .*TargetFactories\\.createTargetB\\(.*SourceB source,"
                    + " @TargetType .*Class<.*TargetB> clazz\\),"
                    + " .*TargetB .*TargetFactories\\.createTargetB\\(@TargetType java.lang.Class<.*TargetB> clazz\\),"
                    + " .*TargetB .*TargetFactories\\.createTargetB\\(\\)."),
            @Diagnostic(type = ErroneousIssue1242MapperMultipleSources.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 33,
                messageRegExp = ".*TargetB does not have an accessible parameterless constructor\\.")
        })
    public void ambiguousMethodErrorForTwoFactoryMethodsWithSourceParam() {
    }
}
