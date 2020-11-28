/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditionalmapping.bad;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.conditionalmapping.Employee;
import org.mapstruct.ap.test.conditionalmapping.EmployeeDto;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({
        Employee.class,
        EmployeeDto.class,
        SimpleEmployeeMapperWithNonMatchingTypes.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class SimpleEmployeeMapperWithNonMatchingTypesTest extends TestCase {

    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
            @Diagnostic(
                    type = SimpleEmployeeMapperWithNonMatchingTypes.class,
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    message = "No conditional method named \"isAmericanCitizen\" found with correct return type to resolve target param \"ssid\""
            )
    })
    public void shouldHaveCompilationErrorIfConditionReturnTypeNotBoolean() {
    }
}