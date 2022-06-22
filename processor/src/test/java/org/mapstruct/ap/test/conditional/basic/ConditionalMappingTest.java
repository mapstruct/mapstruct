/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2051")
@WithClasses({
    BasicEmployee.class,
    BasicEmployeeDto.class
})
public class ConditionalMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInMapper.class
    })
    public void conditionalMethodInMapper() {
        generatedSource.addComparisonToFixtureFor( ConditionalMethodInMapper.class );
        ConditionalMethodInMapper mapper = ConditionalMethodInMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @IssueKey( "2882" )
    @ProcessorTest
    @WithClasses( { ConditionalMethodWithTargetType.class } )
    public void conditionalMethodWithTargetTypeShouldCompile() {
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodAndBeanPresenceCheckMapper.class
    })
    public void conditionalMethodAndBeanPresenceCheckMapper() {
        ConditionalMethodAndBeanPresenceCheckMapper mapper = ConditionalMethodAndBeanPresenceCheckMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new ConditionalMethodAndBeanPresenceCheckMapper.EmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new ConditionalMethodAndBeanPresenceCheckMapper.EmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new ConditionalMethodAndBeanPresenceCheckMapper.EmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInUsesMapper.class
    })
    public void conditionalMethodInUsesMapper() {
        ConditionalMethodInUsesMapper mapper = ConditionalMethodInUsesMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInUsesStaticMapper.class
    })
    public void conditionalMethodInUsesStaticMapper() {
        ConditionalMethodInUsesStaticMapper mapper = ConditionalMethodInUsesStaticMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInContextMapper.class
    })
    public void conditionalMethodInUsesContextMapper() {
        ConditionalMethodInContextMapper mapper = ConditionalMethodInContextMapper.INSTANCE;

        ConditionalMethodInContextMapper.PresenceUtils utils = new ConditionalMethodInContextMapper.PresenceUtils();
        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ), utils );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ), utils );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ), utils );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithSourceParameterMapper.class
    })
    public void conditionalMethodWithSourceParameter() {
        ConditionalMethodWithSourceParameterMapper mapper = ConditionalMethodWithSourceParameterMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "Tester", "map" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithSourceParameterAndValueMapper.class
    })
    public void conditionalMethodWithSourceParameterAndValue() {
        generatedSource.addComparisonToFixtureFor( ConditionalMethodWithSourceParameterAndValueMapper.class );
        ConditionalMethodWithSourceParameterAndValueMapper mapper =
            ConditionalMethodWithSourceParameterAndValueMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "    ", "empty" ) );
        assertThat( employee.getName() ).isEqualTo( "    " );

        employee = mapper.map( new BasicEmployeeDto( "    ", "blank" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "Tester", "blank" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );
    }

    @ProcessorTest
    @WithClasses({
        ErroneousAmbiguousConditionalMethodMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousAmbiguousConditionalMethodMapper.class,
                line = 17,
                message = "Ambiguous presence check methods found for checking String: " +
                    "boolean isNotBlank(String value), " +
                    "boolean isNotEmpty(String value). " +
                    "See https://mapstruct.org/faq/#ambiguous for more info."
            )
        }
    )
    public void ambiguousConditionalMethod() {

    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodForCollectionMapper.class
    })
    public void conditionalMethodForCollection() {
        ConditionalMethodForCollectionMapper mapper = ConditionalMethodForCollectionMapper.INSTANCE;

        ConditionalMethodForCollectionMapper.Author author = new ConditionalMethodForCollectionMapper.Author();
        ConditionalMethodForCollectionMapper.AuthorDto dto = mapper.map( author );

        assertThat( dto.getBooks() ).isNull();

        author.setBooks( Collections.emptyList() );
        dto = mapper.map( author );

        assertThat( dto.getBooks() ).isNull();

        author.setBooks( Arrays.asList(
            new ConditionalMethodForCollectionMapper.Book( "Test" ),
            new ConditionalMethodForCollectionMapper.Book( "Test Vol. 2" )
        ) );
        dto = mapper.map( author );

        assertThat( dto.getBooks() )
            .extracting( ConditionalMethodForCollectionMapper.BookDto::getName )
            .containsExactly( "Test", "Test Vol. 2" );
    }

    @ProcessorTest
    @WithClasses({
        OptionalLikeConditionalMapper.class
    })
    @IssueKey("2084")
    public void optionalLikeConditional() {
        OptionalLikeConditionalMapper mapper = OptionalLikeConditionalMapper.INSTANCE;

        OptionalLikeConditionalMapper.Target target = mapper.map( new OptionalLikeConditionalMapper.Source(
            OptionalLikeConditionalMapper.Nullable.ofNullable( "test" ) ) );

        assertThat( target.getValue() ).isEqualTo( "test" );

        target = mapper.map(
            new OptionalLikeConditionalMapper.Source( OptionalLikeConditionalMapper.Nullable.undefined() )
        );

        assertThat( target.getValue() ).isEqualTo( "initial" );

    }

    @ProcessorTest
    @WithClasses( {
        ConditionalMethodWithMappingTargetInUpdateMapper.class
    } )
    @IssueKey( "2758" )
    public void conditionalMethodWithMappingTarget() {
        ConditionalMethodWithMappingTargetInUpdateMapper mapper =
            ConditionalMethodWithMappingTargetInUpdateMapper.INSTANCE;

        BasicEmployee targetEmployee = new BasicEmployee();
        targetEmployee.setName( "CurrentName" );
        mapper.map( new BasicEmployeeDto( "ReplacementName" ), targetEmployee );

        assertThat( targetEmployee.getName() ).isEqualTo( "CurrentName" );
    }
}
