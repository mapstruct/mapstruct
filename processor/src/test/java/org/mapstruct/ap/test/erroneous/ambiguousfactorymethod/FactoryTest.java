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
package org.mapstruct.ap.test.erroneous.ambiguousfactorymethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.a.BarFactory;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 */
@IssueKey("81")
@WithClasses({
    Bar.class, Foo.class, BarFactory.class, Source.class, SourceTargetMapperAndBarFactory.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class FactoryTest {

    @Test
    @IssueKey("81")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAndBarFactory.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "Ambiguous factory methods found for creating "
                        + "org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar: "
                        + "org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar createBar\\(\\), "
                        + "org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar .*BarFactory.createBar\\(\\)." )
        }
    )
    public void shouldUseTwoFactoryMethods() {
    }
}
