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
package org.mapstruct.ap.test.severalsources;

import javax.tools.Diagnostic.Kind;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for propagation of attribute without setter in source and getter in
 * target.
 *
 * @author Gunnar Morling
 */
@IssueKey("31")
@RunWith(AnnotationProcessorTestRunner.class)
public class SeveralSourceParametersTest {

    @Before
    public void reset() {
        ReferencedMapper.setBeforeMappingCalled( false );
    }

    @Test
    @WithClasses( {
        Person.class,
        Address.class,
        DeliveryAddress.class,
        SourceTargetMapper.class,
        ReferencedMapper.class } )
    public void shouldMapSeveralSourceAttributesToCombinedTarget() {
        Person person = new Person( "Bob", "Garner", 181, "An actor" );
        Address address = new Address( "Main street", 12345, 42, "His address" );

        DeliveryAddress deliveryAddress = SourceTargetMapper.INSTANCE
            .personAndAddressToDeliveryAddress( person, address );

        assertThat( deliveryAddress ).isNotNull();
        assertThat( deliveryAddress.getLastName() ).isEqualTo( "Garner" );
        assertThat( deliveryAddress.getZipCode() ).isEqualTo( 12345 );
        assertThat( deliveryAddress.getHouseNumber() ).isEqualTo( 42 );
        assertThat( deliveryAddress.getDescription() ).isEqualTo( "An actor" );

        assertThat( ReferencedMapper.isBeforeMappingCalled() ).isTrue();
    }

    @Test
    @WithClasses( {
        Person.class,
        Address.class,
        DeliveryAddress.class,
        SourceTargetMapper.class,
        ReferencedMapper.class } )
    public void shouldMapSeveralSourceAttributesToCombinedTargetWithTargetParameter() {
        Person person = new Person( "Bob", "Garner", 181, "An actor" );
        Address address = new Address( "Main street", 12345, 42, "His address" );

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        SourceTargetMapper.INSTANCE.personAndAddressToDeliveryAddress( person, address, deliveryAddress );

        assertThat( deliveryAddress.getLastName() ).isEqualTo( "Garner" );
        assertThat( deliveryAddress.getZipCode() ).isEqualTo( 12345 );
        assertThat( deliveryAddress.getHouseNumber() ).isEqualTo( 42 );
        assertThat( deliveryAddress.getDescription() ).isEqualTo( "An actor" );

        assertThat( ReferencedMapper.isBeforeMappingCalled() ).isTrue();
    }

    @Test
    @WithClasses( {
        Person.class,
        Address.class,
        DeliveryAddress.class,
        SourceTargetMapper.class,
        ReferencedMapper.class } )
    public void shouldSetAttributesFromNonNullParameters() {
        Person person = new Person( "Bob", "Garner", 181, "An actor" );

        DeliveryAddress deliveryAddress = SourceTargetMapper.INSTANCE
            .personAndAddressToDeliveryAddress( person, null );

        assertThat( deliveryAddress ).isNotNull();
        assertThat( deliveryAddress.getLastName() ).isEqualTo( "Garner" );
        assertThat( deliveryAddress.getDescription() ).isEqualTo( "An actor" );
        assertThat( deliveryAddress.getHouseNumber() ).isEqualTo( 0 );
        assertThat( deliveryAddress.getStreet() ).isNull();
    }

    @Test
    @WithClasses( {
        Person.class,
        Address.class,
        DeliveryAddress.class,
        SourceTargetMapper.class,
        ReferencedMapper.class } )
    public void shouldReturnNullIfAllParametersAreNull() {
        DeliveryAddress deliveryAddress = SourceTargetMapper.INSTANCE
            .personAndAddressToDeliveryAddress( null, null );

        assertThat( deliveryAddress ).isNull();
    }

    @Test
    @WithClasses( {
        Person.class,
        Address.class,
        DeliveryAddress.class,
        SourceTargetMapper.class,
        ReferencedMapper.class } )
    public void shouldMapSeveralSourceAttributesAndParameters() {
        Person person = new Person( "Bob", "Garner", 181, "An actor" );

        DeliveryAddress deliveryAddress =
                SourceTargetMapper.INSTANCE.personAndAddressToDeliveryAddress( person, 42, 12345, "Main street" );

        assertThat( deliveryAddress.getLastName() ).isEqualTo( "Garner" );
        assertThat( deliveryAddress.getZipCode() ).isEqualTo( 12345 );
        assertThat( deliveryAddress.getHouseNumber() ).isEqualTo( 42 );
        assertThat( deliveryAddress.getDescription() ).isEqualTo( "An actor" );
        assertThat( deliveryAddress.getStreet() ).isEqualTo( "Main street" );
    }

    @Test
    @WithClasses({ ErroneousSourceTargetMapper.class, Address.class, DeliveryAddress.class })
    @ProcessorOption(name = "mapstruct.unmappedTargetPolicy", value = "IGNORE")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "Several possible source properties for target property \"street\"."),
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "Several possible source properties for target property \"zipCode\"."),
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "Several possible source properties for target property \"description\".")
    })
    public void shouldFailToGenerateMappingsForAmbigiousSourceProperty() {
    }
}
