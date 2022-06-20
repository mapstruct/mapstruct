/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1828;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1828")
@WithClasses({
    CompleteAddress.class,
    Employee.class,
    FirstMapper.class,
    GeneralAddress.class,
    Person.class,
    SpecialAddress.class,
})
public class Issue1828Test {

    @ProcessorTest
    public void testMapSpecialAndGeneralAddressSet() {

        Employee employee = new Employee();
        employee.setName( "Mad King" );

        SpecialAddress specialAddress = new SpecialAddress();
        specialAddress.setLine1( "Building One" );
        specialAddress.setLine2( "Street Two" );
        employee.setSpecialAddress( specialAddress );

        GeneralAddress generalAddress = new GeneralAddress();
        generalAddress.setCity( "King's Landing" );
        generalAddress.setCountry( "Seven Kingdom" );
        employee.setGeneralAddress( generalAddress );

        Person person = FirstMapper.INSTANCE.mapPerson( employee );
        assertThat( person.getName() ).isEqualTo( "Mad King" );

        CompleteAddress completeAddress = person.getCompleteAddress();
        assertThat( completeAddress ).isNotNull();
        assertThat( completeAddress.getLineOne() ).isEqualTo( "Building One" );
        assertThat( completeAddress.getLineTwo() ).isEqualTo( "Street Two" );
        assertThat( completeAddress.getCity() ).isEqualTo( "King's Landing" );
        assertThat( completeAddress.getCountry() ).isEqualTo( "Seven Kingdom" );
    }

    @ProcessorTest
    public void testMapGeneralAddressNull() {

        Employee employee = new Employee();
        employee.setName( "Mad King" );

        SpecialAddress specialAddress = new SpecialAddress();
        specialAddress.setLine1( "Building One" );
        specialAddress.setLine2( "Street Two" );
        employee.setSpecialAddress( specialAddress );

        Person person = FirstMapper.INSTANCE.mapPerson( employee );
        assertThat( person.getName() ).isEqualTo( "Mad King" );

        CompleteAddress completeAddress = person.getCompleteAddress();
        assertThat( completeAddress ).isNotNull();
        assertThat( completeAddress.getLineOne() ).isEqualTo( "Building One" );
        assertThat( completeAddress.getLineTwo() ).isEqualTo( "Street Two" );
        assertThat( completeAddress.getCity() ).isNull();
        assertThat( completeAddress.getCountry() ).isNull();
    }

    @ProcessorTest
    public void testMapSpecialAddressNull() {

        Employee employee = new Employee();
        employee.setName( "Mad King" );

        GeneralAddress generalAddress = new GeneralAddress();
        generalAddress.setCity( "King's Landing" );
        generalAddress.setCountry( "Seven Kingdom" );
        employee.setGeneralAddress( generalAddress );

        Person person = FirstMapper.INSTANCE.mapPerson( employee );
        assertThat( person.getName() ).isEqualTo( "Mad King" );

        CompleteAddress completeAddress = person.getCompleteAddress();
        assertThat( completeAddress ).isNotNull();
        assertThat( completeAddress.getLineOne() ).isNull();
        assertThat( completeAddress.getLineTwo() ).isNull();
        assertThat( completeAddress.getCity() ).isEqualTo( "King's Landing" );
        assertThat( completeAddress.getCountry() ).isEqualTo( "Seven Kingdom" );
    }
}
