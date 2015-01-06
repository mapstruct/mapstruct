/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.unmappedtarget;

import static org.fest.assertions.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Tests expected diagnostics for unmapped target properties.
 *
 * @author Gunnar Morling
 */
@IssueKey("35")
@RunWith(AnnotationProcessorTestRunner.class)
public class UnmappedTargetTest {

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 29,
                messageRegExp = "Unmapped target property: \"bar\""),
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 31,
                messageRegExp = "Unmapped target property: \"qux\"")
        }
    )
    public void shouldLeaveUnmappedTargetPropertyUnset() {
        Source source = new Source();
        source.setFoo( 42L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( 42L );
        assertThat( target.getBar() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses({ Source.class, Target.class, StrictSourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = StrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = "Unmapped target property: \"bar\""),
            @Diagnostic(type = StrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 32,
                messageRegExp = "Unmapped target property: \"qux\"")
        }
    )
    public void shouldRaiseErrorDueToUnsetTargetProperty() {
    }

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    @ProcessorOption(name = "mapstruct.unmappedTargetPolicy", value = "ERROR")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "Unmapped target property: \"bar\""),
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 31,
                messageRegExp = "Unmapped target property: \"qux\"")
        }
    )
    public void shouldRaiseErrorDueToUnsetTargetPropertyWithPolicySetViaProcessorOption() {
    }
}
