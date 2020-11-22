/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manysourcearguments;

import static org.assertj.core.api.Assertions.assertThat;

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

/**
 * Test for propagation of attribute without setter in source and getter in
 * target.
 *
 * @author Gunnar Morling
 */
@IssueKey("31")
@RunWith(AnnotationProcessorTestRunner.class)
public class ManySourceArgumentsTest {

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

    @IssueKey( "1593" )
    @Test
    @WithClasses( {
        Person.class,
        Address.class,
        DeliveryAddress.class,
        SourceTargetMapperWithConfig.class,
        SourceTargetConfig.class } )
    public void shouldUseConfig() {
        Person person = new Person( "Bob", "Garner", 181, "An actor" );
        Address address = new Address( "Main street", 12345, 42, "His address" );

        DeliveryAddress deliveryAddress = SourceTargetMapperWithConfig.INSTANCE
            .personAndAddressToDeliveryAddress( person, address );

        assertThat( deliveryAddress ).isNotNull();
        assertThat( deliveryAddress.getLastName() ).isEqualTo( "Garner" );
        assertThat( deliveryAddress.getZipCode() ).isEqualTo( 12345 );
        assertThat( deliveryAddress.getHouseNumber() ).isEqualTo( 42 );
        assertThat( deliveryAddress.getDescription() ).isEqualTo( "An actor" );

    }

    @Test
    @WithClasses({ ErroneousSourceTargetMapper.class, Address.class, DeliveryAddress.class })
    @ProcessorOption(name = "mapstruct.unmappedTargetPolicy", value = "IGNORE")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 16,
                message = "Several possible source properties for target property \"street\"."),
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 16,
                message = "Several possible source properties for target property \"zipCode\"."),
            @Diagnostic(type = ErroneousSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 16,
                message = "Several possible source properties for target property \"description\".")
    })
    public void shouldFailToGenerateMappingsForAmbiguousSourceProperty() {
    }

    @Test
    @WithClasses({
        ErroneousSourceTargetMapper2.class,
        Address.class,
        Person.class,
        DeliveryAddress.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousSourceTargetMapper2.class,
                kind = Kind.ERROR,
                line = 15,
                message = "Method has no source parameter named \"houseNo\"." +
                    " Method source parameters are: \"address, person\"."
            )
        }
    )
    public void shouldFailWhenSourcePropertyDoesNotMatchAnyOfTheSourceParameters() {
    }
}
