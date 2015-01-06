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
package org.mapstruct.ap.test.inheritance.attribute;

import static org.fest.assertions.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for setting an attribute where the target attribute of a super-type.
 *
 * @author Gunnar Morling
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class AttributeInheritanceTest {

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void shouldMapAttributeFromSuperType() {
        Source source = new Source();
        source.setFoo( "Bob" );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().toString() ).isEqualTo( "Bob" );
    }

    @Test
    @WithClasses({ Source.class, Target.class, TargetSourceMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            type = TargetSourceMapper.class,
            kind = Kind.ERROR,
            line = 29,
            messageRegExp = "Can't map property \"java.lang.CharSequence foo\" to \"java.lang.String foo\""
        ))
    public void shouldReportErrorDueToUnmappableAttribute() {
    }
}
