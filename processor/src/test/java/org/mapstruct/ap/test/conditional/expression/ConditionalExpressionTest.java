/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.expression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.ap.test.conditional.basic.BasicEmployee;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2051")
@WithClasses({
    Employee.class,
    EmployeeDto.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class ConditionalExpressionTest {

    @Test
    @WithClasses({
        ConditionalMethodsInUtilClassMapper.class
    })
    public void conditionalExpressionInStaticClassMethod() {
        ConditionalMethodsInUtilClassMapper mapper = ConditionalMethodsInUtilClassMapper.INSTANCE;

        EmployeeDto dto = new EmployeeDto();
        dto.setName( "Tester" );
        dto.setUniqueIdNumber( "SSID-001" );
        dto.setCountry( null );

        Employee employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isNull();

        dto.setCountry( "UK" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isEqualTo( "SSID-001" );
        assertThat( employee.getSsid() ).isNull();

        dto.setCountry( "US" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isEqualTo( "SSID-001" );

        dto.setCountry( "CH" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isNull();
    }

    @Test
    @WithClasses({
        SimpleConditionalExpressionMapper.class
    })
    public void conditionalSimpleExpression() {
        SimpleConditionalExpressionMapper mapper = SimpleConditionalExpressionMapper.INSTANCE;

        SimpleConditionalExpressionMapper.Target target =
            mapper.map( new SimpleConditionalExpressionMapper.Source( 50 ) );
        assertThat( target.getValue() ).isEqualTo( 50 );

        target = mapper.map( new SimpleConditionalExpressionMapper.Source( 101 ) );
        assertThat( target.getValue() ).isEqualTo( 0 );
    }

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousConditionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Value for condition expression must be given in the form \"java(<EXPRESSION>)\"."
            )
        }
    )
    @WithClasses({
        BasicEmployee.class,
        ErroneousConditionExpressionMapper.class
    } )
    public void invalidJavaConditionExpression() {
    }
}
