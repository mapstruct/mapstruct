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
package org.mapstruct.ap.test.erroneous.propertymapping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey("1504")
@WithClasses( { Source.class, Target.class, UnmappableClass.class } )
@RunWith(AnnotationProcessorTestRunner.class)
public class ErroneousPropertyMappingTest {

    @Test
    @WithClasses( ErroneousMapper1.class )
    @IssueKey("1504")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 29,
            messageRegExp = ".*Consider to declare/implement a mapping method.*") }
    )
    public void testUnmappableSourceProperty() { }

    @Test
    @WithClasses( ErroneousMapper2.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = ".*Consider to declare/implement a mapping method.*") }
    )
    public void testUnmappableSourcePropertyWithNoSourceDefinedInMapping() { }

    @Test
    @WithClasses( ErroneousMapper3.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map.*constant.*" ) }
    )
    public void testUnmappableConstantAssignment() { }

    @Test
    @WithClasses( ErroneousMapper4.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = ".*Consider to declare/implement a mapping method.*") }
    )
    public void testUnmappableParameterAssignment() { }
}
