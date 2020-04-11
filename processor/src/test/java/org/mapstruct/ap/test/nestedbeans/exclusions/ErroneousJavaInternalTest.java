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
                message = "Can't map property \"org.mapstruct.ap.test.nestedbeans.exclusions.Source.MyType date\" to " +
                    "\"java.util.Date date\". Consider to declare/implement a mapping method: \"java.util.Date map" +
                    "(org.mapstruct.ap.test.nestedbeans.exclusions.Source.MyType value)\"."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"org.mapstruct.ap.test.nestedbeans.exclusions.Source.MyType calendar\"" +
                    " to \"java.util.GregorianCalendar calendar\". Consider to declare/implement a mapping method: " +
                    "\"java.util.GregorianCalendar map(org.mapstruct.ap.test.nestedbeans.exclusions.Source.MyType " +
                    "value)\"."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"java.util.List<org.mapstruct.ap.test.nestedbeans.exclusions.Source" +
                    ".MyType> types\" to \"java.util.List<java.lang.String> types\". Consider to declare/implement a " +
                    "mapping method: \"java.util.List<java.lang.String> map(java.util.List<org.mapstruct.ap.test" +
                    ".nestedbeans.exclusions.Source.MyType> value)\"."),
            @Diagnostic(type = ErroneousJavaInternalMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"java.util.List<org.mapstruct.ap.test.nestedbeans.exclusions.Source" +
                    ".MyType> nestedMyType.deepNestedType.types\" to \"java.util.List<java.lang.String> nestedMyType" +
                    ".deepNestedType.types\". Consider to declare/implement a mapping method: \"java.util.List<java" +
                    ".lang.String> map(java.util.List<org.mapstruct.ap.test.nestedbeans.exclusions.Source.MyType> " +
                    "value)\".")
        })
    @Test
    public void shouldNotNestIntoJavaPackageObjects() {
    }
}
