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
package org.mapstruct.ap.test.dependency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Mapping;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for ordering mapped attributes by means of {@link Mapping#dependsOn()}.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Person.class, PersonDto.class, Address.class, AddressDto.class, AddressMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class OrderingTest {

    @Test
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

    @Test
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

    @Test
    @IssueKey("304")
    @WithClasses(AddressMapperWithCyclicDependency.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = AddressMapperWithCyclicDependency.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 36,
                messageRegExp = "Cycle\\(s\\) between properties given via dependsOn\\(\\): firstName -> lastName -> "
                    + "middleName -> firstName"
            )
        }
    )
    public void shouldReportErrorIfDependenciesContainCycle() {
    }

    @Test
    @IssueKey("304")
    @WithClasses(AddressMapperWithUnknownPropertyInDependsOn.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = AddressMapperWithUnknownPropertyInDependsOn.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 32,
                messageRegExp = "\"doesnotexist\" is no property of the method return type"
            )
        }
    )
    public void shouldReportErrorIfPropertiyGivenInDependsOnDoesNotExist() {
    }
}
