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
package org.mapstruct.ap.test.bugs._1153;

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
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(ErroneousIssue1153Mapper.class)
@IssueKey("1153")
public class Issue1153Test {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                messageRegExp = "Property \"readOnly\" has no write accessor in " +
                    "org.mapstruct.ap.test.bugs._1153.ErroneousIssue1153Mapper.Target\\."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 33,
                messageRegExp = "Property \"nestedTarget.readOnly\" has no write accessor in " +
                    "org.mapstruct.ap.test.bugs._1153.ErroneousIssue1153Mapper.Target\\."),
            @Diagnostic(type = ErroneousIssue1153Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 36,
                messageRegExp = "Unknown property \"nestedTarget2.writable2\" in result type " +
                    "org.mapstruct.ap.test.bugs._1153.ErroneousIssue1153Mapper.Target\\. " +
                    "Did you mean \"nestedTarget2\\.writable\"")
        })
    @Test
    public void shouldReportErrorsCorrectly() {
    }
}
