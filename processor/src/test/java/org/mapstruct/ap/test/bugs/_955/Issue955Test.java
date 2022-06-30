/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._955;

import org.mapstruct.ap.test.bugs._955.dto.Car;
import org.mapstruct.ap.test.bugs._955.dto.Person;
import org.mapstruct.ap.test.bugs._955.dto.SuperCar;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "955" )
@WithClasses( { CarMapper.class, CustomMapper.class, Car.class, SuperCar.class, Person.class } )
public class Issue955Test {

    @ProcessorTest
    public void shouldCompile() {   }
}
