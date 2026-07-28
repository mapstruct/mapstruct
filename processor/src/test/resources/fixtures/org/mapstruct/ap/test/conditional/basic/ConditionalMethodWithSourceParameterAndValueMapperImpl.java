/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:55:45+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class ConditionalMethodWithSourceParameterAndValueMapperImpl implements ConditionalMethodWithSourceParameterAndValueMapper {

    @Override
    public BasicEmployee map(BasicEmployeeDto employee) {
        if ( employee == null ) {
            return null;
        }

        BasicEmployee basicEmployee = new BasicEmployee();

        String name = employee.getName();
        if ( isPresent( employee, name ) ) {
            basicEmployee.setName( name );
        }

        return basicEmployee;
    }
}
