/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.Arrays;

public class TestData {

    private TestData() {

    }

    public static User createUser() {
        return new User( "John", new Car( "Chrysler", 1955, Arrays.asList(
            new Wheel().front().left(),
            new Wheel().front().right(),
            new Wheel().rear().left(),
            new Wheel().rear().right()
        ) ),
            new House( "Black", 1834, new Roof( 1, RoofType.BOX ) )
        );
    }

}
