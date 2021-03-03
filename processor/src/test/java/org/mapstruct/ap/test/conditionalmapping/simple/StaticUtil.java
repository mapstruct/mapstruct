/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditionalmapping.simple;

import org.mapstruct.ap.test.conditionalmapping.EmployeeDto;

public class StaticUtil {

    private StaticUtil() {
        //empty
    }

    public static boolean isBritishCitizen(EmployeeDto employeeDto) {
        return "UK".equals( employeeDto.getCountry() );
    }

}
