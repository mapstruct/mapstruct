/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._955;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._955.dto.Car;
import org.mapstruct.ap.test.bugs._955.dto.Person;
import org.mapstruct.ap.test.bugs._955.dto.SuperCar;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "955" )
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses( { CarMapper.class, CustomMapper.class, Car.class, SuperCar.class, Person.class } )
public class Issue955Test {

    @Test
    public void shouldCompile() {   }
}
