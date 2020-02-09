/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping;

import java.util.Arrays;
import java.util.function.BiConsumer;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@IssueKey("1306")
@WithClasses({
    Address.class,
    Customer.class,
    CustomerDTO.class,
    AddressDTO.class,
    HomeDTO.class,
    UserDTO.class
})
public class NullValuePropertyMappingTest {

    @ProcessorTest
    @WithClasses(CustomerMapper.class)
    public void testStrategyAppliedOnForgedMethod() {

        Customer customer = new Customer();
        customer.setAddress( null );

        UserDTO userDTO = new UserDTO();
        userDTO.setHomeDTO( new HomeDTO() );
        userDTO.getHomeDTO().setAddressDTO( new AddressDTO() );
        userDTO.getHomeDTO().getAddressDTO().setHouseNo( 5 );
        userDTO.setDetails( Arrays.asList( "green hair" ) );

        CustomerMapper.INSTANCE.mapCustomer( customer, userDTO );

        assertThat( userDTO.getHomeDTO() ).isNotNull();
        assertThat( userDTO.getHomeDTO().getAddressDTO() ).isNotNull();
        assertThat( userDTO.getHomeDTO().getAddressDTO().getHouseNo() ).isEqualTo( 5 );
        assertThat( userDTO.getDetails() ).isNotNull();
        assertThat( userDTO.getDetails() ).containsExactly( "green hair" );
    }

    @ProcessorTest
    @WithClasses({ NvpmsConfig.class, CustomerNvpmsOnConfigMapper.class })
    public void testHierarchyIgnoreOnConfig() {
        testConfig( CustomerNvpmsOnConfigMapper.INSTANCE::map );
    }

    @ProcessorTest
    @WithClasses(CustomerNvpmsOnMapperMapper.class)
    public void testHierarchyIgnoreOnMapping() {
        testConfig( CustomerNvpmsOnMapperMapper.INSTANCE::map );
    }

    @ProcessorTest
    @WithClasses(CustomerNvpmsOnBeanMappingMethodMapper.class)
    public void testHierarchyIgnoreOnBeanMappingMethod() {
        testConfig( CustomerNvpmsOnBeanMappingMethodMapper.INSTANCE::map );
    }

    @ProcessorTest
    @WithClasses(CustomerNvpmsPropertyMappingMapper.class)
    public void testHierarchyIgnoreOnPropertyMappingMethod() {
        testConfig( CustomerNvpmsPropertyMappingMapper.INSTANCE::map );
    }

    @ProcessorTest
    @WithClasses(CustomerDefaultMapper.class)
    public void testStrategyDefaultAppliedOnForgedMethod() {

        Customer customer = new Customer();
        customer.setAddress( null );

        UserDTO userDTO = new UserDTO();
        userDTO.setHomeDTO( new HomeDTO() );
        userDTO.getHomeDTO().setAddressDTO( new AddressDTO() );
        userDTO.getHomeDTO().getAddressDTO().setHouseNo( 5 );
        userDTO.setDetails( Arrays.asList( "green hair" ) );

        CustomerDefaultMapper.INSTANCE.mapCustomer( customer, userDTO );

        assertThat( userDTO.getHomeDTO() ).isNotNull();
        assertThat( userDTO.getHomeDTO().getAddressDTO() ).isNotNull();
        assertThat( userDTO.getHomeDTO().getAddressDTO().getHouseNo() ).isNull();
        assertThat( userDTO.getDetails() ).isNotNull();
        assertThat( userDTO.getDetails() ).isEmpty();
    }

    @ProcessorTest
    @WithClasses(ErroneousCustomerMapper1.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomerMapper1.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                alternativeLine = 22, // Javac wrong error reporting on repeatable annotations JDK-8042710
                messageRegExp = "Default value and nullValuePropertyMappingStrategy are both defined in @Mapping, " +
                    "either define a defaultValue or an nullValuePropertyMappingStrategy.")
        }
    )
    public void testBothDefaultValueAndNvpmsDefined() {
    }

    @ProcessorTest
    @WithClasses(ErroneousCustomerMapper2.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomerMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                alternativeLine = 22, // Javac wrong error reporting on repeatable annotations JDK-8042710
                messageRegExp = "Expression and nullValuePropertyMappingStrategy are both defined in @Mapping, " +
                    "either define an expression or an nullValuePropertyMappingStrategy.")
        }
    )
    public void testBothExpressionAndNvpmsDefined() {
    }

    @ProcessorTest
    @WithClasses(ErroneousCustomerMapper3.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomerMapper3.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                alternativeLine = 22, // Javac wrong error reporting on repeatable annotations JDK-8042710
                messageRegExp = "DefaultExpression and nullValuePropertyMappingStrategy are both defined in " +
                    "@Mapping, either define a defaultExpression or an nullValuePropertyMappingStrategy.")
        }
    )
    public void testBothDefaultExpressionAndNvpmsDefined() {
    }

    @ProcessorTest
    @WithClasses(ErroneousCustomerMapper4.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomerMapper4.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                alternativeLine = 22, // Javac wrong error reporting on repeatable annotations JDK-8042710
                messageRegExp = "Constant and nullValuePropertyMappingStrategy are both defined in @Mapping, " +
                    "either define a constant or an nullValuePropertyMappingStrategy.")
        }
    )
    public void testBothConstantAndNvpmsDefined() {
    }

    @ProcessorTest
    @WithClasses(ErroneousCustomerMapper5.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomerMapper5.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                alternativeLine = 22, // Javac wrong error reporting on repeatable annotations JDK-8042710
                messageRegExp = "Ignore and nullValuePropertyMappingStrategy are both defined in @Mapping, " +
                    "either define ignore or an nullValuePropertyMappingStrategy.")
        }
    )
    public void testBothIgnoreAndNvpmsDefined() {
    }

    private void testConfig(BiConsumer<Customer, CustomerDTO> customerMapper) {

        Customer customer = new Customer();
        customer.setAddress( null );

        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setAddress( new AddressDTO() );
        customerDto.getAddress().setHouseNo( 5 );
        customerDto.setDetails( Arrays.asList( "green hair" ) );

        customerMapper.accept( customer, customerDto );

        assertThat( customerDto.getAddress() ).isNotNull();
        assertThat( customerDto.getAddress().getHouseNo() ).isEqualTo( 5 );
        assertThat( customerDto.getDetails() ).isNotNull();
        assertThat( customerDto.getDetails() ).containsExactly( "green hair" );
    }
}
