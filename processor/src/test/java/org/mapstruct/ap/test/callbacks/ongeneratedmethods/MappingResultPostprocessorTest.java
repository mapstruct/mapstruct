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
package org.mapstruct.ap.test.callbacks.ongeneratedmethods;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 * @author Sjaak Derksen
 */

@WithClasses({
    Company.class,
    CompanyDto.class,
    Employee.class,
    EmployeeDto.class,
    Address.class,
    AddressDto.class,
    CompanyMapper.class,
    CompanyMapperPostProcessing.class
})
@IssueKey("183")
@RunWith(AnnotationProcessorTestRunner.class)
public class MappingResultPostprocessorTest {

    @Test
    public void test() {

        // setup
        Address address = new Address();
        address.setAddressLine( "RoadToNowhere;5");
        address.setTown( "SmallTown" );
        Employee employee = new Employee();
        employee.setAddress( address );
        Company company = new Company();
        company.setEmployees( Arrays.asList( new Employee[] { employee } ) );

        // test
        CompanyDto companyDto = CompanyMapper.INSTANCE.toCompanyDto( company );

        // verify
        assertThat( companyDto.getEmployees() ).isNotEmpty();
        assertThat( companyDto.getEmployees().size() ).isEqualTo( 1 );
        assertThat( companyDto.getEmployees().get( 0 ).getAddress() ).isNotNull();
        assertThat( companyDto.getEmployees().get( 0 ).getAddress().getHouseNumber() ).isEqualTo( 5 );
        assertThat( companyDto.getEmployees().get( 0 ).getAddress().getStreet() ).isEqualTo( "RoadToNowhere" );
        assertThat( companyDto.getEmployees().get( 0 ).getAddress().getTown() ).isEqualTo( "SmallTown" );
    }

}
