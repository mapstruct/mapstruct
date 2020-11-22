/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exclusions;

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
@WithClasses({
    Source.class,
    Target.class,
    ErroneousJavaInternalMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1154")
public class ErroneousJavaInternalTest {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"Source.MyType date\" to \"Date date\". " +
                    "Consider to declare/implement a mapping method: \"Date map(Source.MyType value)\"."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"Source.MyType calendar\" to \"GregorianCalendar calendar\". " +
                    "Consider to declare/implement a mapping method: \"GregorianCalendar map(Source.MyType value)\"."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"List<Source.MyType> types\" to \"List<String> types\". " +
                    "Consider to declare/implement a mapping method: \"List<String> map(List<Source.MyType> value)\"."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"List<Source.MyType> nestedMyType.deepNestedType.types\" to " +
                    "\"List<String> nestedMyType.deepNestedType.types\". " +
                    "Consider to declare/implement a mapping method: " +
                    "\"List<String> map(List<Source.MyType> value)\".")
        })
    @Test
    public void shouldNotNestIntoJavaPackageObjects() {
    }
}
