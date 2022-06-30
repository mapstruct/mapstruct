/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.dependency;

import org.mapstruct.Mapping;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for ordering mapped attributes by means of {@link Mapping#dependsOn()}.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Person.class, PersonDto.class, Address.class, AddressDto.class, AddressMapper.class })
public class OrderingTest {

    @ProcessorTest
    @IssueKey("304")
    public void shouldApplyChainOfDependencies() {
        Address source = new Address();
        source.setFirstName( "Bob" );
        source.setMiddleName( "J." );
        source.setLastName( "McRobb" );

        AddressDto target = AddressMapper.INSTANCE.addressToDto( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFullName() ).isEqualTo( "Bob J. McRobb" );
    }

    @ProcessorTest
    @IssueKey("304")
    public void shouldApplySeveralDependenciesConfiguredForOneProperty() {
        Person source = new Person();
        source.setFirstName( "Bob" );
        source.setMiddleName( "J." );
        source.setLastName( "McRobb" );

        PersonDto target = AddressMapper.INSTANCE.personToDto( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFullName() ).isEqualTo( "Bob J. McRobb" );
    }

    @ProcessorTest
    @IssueKey("304")
    @WithClasses(ErroneousAddressMapperWithCyclicDependency.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousAddressMapperWithCyclicDependency.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                message = "Cycle(s) between properties given via dependsOn(): lastName -> middleName -> "
                    + "firstName -> lastName."
            )
        }
    )
    public void shouldReportErrorIfDependenciesContainCycle() {
    }

    @ProcessorTest
    @IssueKey("304")
    @WithClasses(ErroneousAddressMapperWithUnknownPropertyInDependsOn.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousAddressMapperWithUnknownPropertyInDependsOn.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "\"doesnotexist\" is no property of the method return type."
            )
        }
    )
    public void shouldReportErrorIfPropertyGivenInDependsOnDoesNotExist() {
    }
}
