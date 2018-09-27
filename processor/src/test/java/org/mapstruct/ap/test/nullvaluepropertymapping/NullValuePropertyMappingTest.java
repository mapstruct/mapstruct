/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Address.class,
    Customer.class,
    CustomerDTO.class,
    AddressDTO.class,
    HomeDTO.class,
    UserDTO.class
})
public class NullValuePropertyMappingTest {

    @Test
    @WithClasses(CustomerMapper.class)
    @IssueKey("1306")
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

    @Test
    @WithClasses({ NvpmsConfig.class, CustomerNvpmsOnConfigMapper.class })
    public void testHierarchyIgnoreOnConfig() {
        testConfig( ( Customer s, CustomerDTO t ) -> CustomerNvpmsOnConfigMapper.INSTANCE.map( s, t ) );
    }

    @Test
    @WithClasses(CustomerNvpmsOnMapperMapper.class)
    public void testHierarchyIgnoreOnMapping() {
        testConfig( ( Customer s, CustomerDTO t ) -> CustomerNvpmsOnMapperMapper.INSTANCE.map( s, t ) );
    }

    @Test
    @WithClasses(CustomerNvpmsOnBeanMappingMethodMapper.class)
    public void testHierarchyIgnoreOnBeanMappingMethod() {
        testConfig( ( Customer s, CustomerDTO t ) -> CustomerNvpmsOnBeanMappingMethodMapper.INSTANCE.map( s, t ) );
    }

    @Test
    @WithClasses(CustomerNvpmsPropertyMappingMapper.class)
    public void testHierarchyIgnoreOnPropertyMappingMehtod() {
        testConfig( ( Customer s, CustomerDTO t ) -> CustomerNvpmsPropertyMappingMapper.INSTANCE.map( s, t ) );
    }

    @Test
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

    @Test
    @Ignore // test gives different results for JDK and JDT
    @WithClasses(ErroneousCustomerMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomerMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                messageRegExp = "Default value and nullValuePropertyMappingStrategy are both defined in @Mapping, " +
                    "either define a defaultValue or an nullValuePropertyMappingStrategy.")
        }
    )
    public void testBothDefaultValueANdNullValuePropertyMappingStrategyDefined() {
    }

    @FunctionalInterface
    interface CustomerFI {
        void apply(Customer source, CustomerDTO target);
    }

    private void testConfig(CustomerFI customerMapper) {

        Customer customer = new Customer();
        customer.setAddress( null );

        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setAddress( new AddressDTO() );
        customerDto.getAddress().setHouseNo( 5 );
        customerDto.setDetails( Arrays.asList( "green hair" ) );

        customerMapper.apply( customer, customerDto );

        assertThat( customerDto.getAddress() ).isNotNull();
        assertThat( customerDto.getAddress().getHouseNo() ).isEqualTo( 5 );
        assertThat( customerDto.getDetails() ).isNotNull();
        assertThat( customerDto.getDetails() ).containsExactly( "green hair" );
    }
}
