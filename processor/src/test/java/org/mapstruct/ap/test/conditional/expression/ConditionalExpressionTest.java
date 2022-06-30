/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.expression;

import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.ap.test.conditional.basic.BasicEmployee;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2051")
@WithClasses({
    Employee.class,
    EmployeeDto.class
})
public class ConditionalExpressionTest {

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
    @WithClasses({
        ConditionalWithSourceToTargetExpressionMapper.class
    })
    @IssueKey("2666")
    public void conditionalExpressionForSourceToTarget() {
        ConditionalWithSourceToTargetExpressionMapper mapper = ConditionalWithSourceToTargetExpressionMapper.INSTANCE;

        ConditionalWithSourceToTargetExpressionMapper.OrderDTO orderDto =
            new ConditionalWithSourceToTargetExpressionMapper.OrderDTO();

        ConditionalWithSourceToTargetExpressionMapper.Order order = mapper.convertToOrder( orderDto );
        assertThat( order ).isNotNull();
        assertThat( order.getCustomer() ).isNull();

        orderDto = new ConditionalWithSourceToTargetExpressionMapper.OrderDTO();
        orderDto.setCustomerName( "Tester" );
        order = mapper.convertToOrder( orderDto );

        assertThat( order ).isNotNull();
        assertThat( order.getCustomer() ).isNotNull();
        assertThat( order.getCustomer().getName() ).isEqualTo( "Tester" );
        assertThat( order.getCustomer().getAddress() ).isNull();

        orderDto = new ConditionalWithSourceToTargetExpressionMapper.OrderDTO();
        orderDto.setLine1( "Line 1" );
        order = mapper.convertToOrder( orderDto );

        assertThat( order ).isNotNull();
        assertThat( order.getCustomer() ).isNotNull();
        assertThat( order.getCustomer().getName() ).isNull();
        assertThat( order.getCustomer().getAddress() ).isNotNull();
        assertThat( order.getCustomer().getAddress().getLine1() ).isEqualTo( "Line 1" );
        assertThat( order.getCustomer().getAddress().getLine2() ).isNull();

    }

    @IssueKey("2794")
    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousConditionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Value for condition expression must be given in the form \"java(<EXPRESSION>)\"."
            ),
            @Diagnostic(type = ErroneousConditionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Constant and condition expression are both defined in @Mapping,"
                    + " either define a constant or a condition expression."
            ),
            @Diagnostic(type = ErroneousConditionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 25,
                message = "Expression and condition expression are both defined in @Mapping,"
                    + " either define an expression or a condition expression."
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
