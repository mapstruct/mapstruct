/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-19T21:10:38+0200",
    comments = "version: , compiler: javac, environment: Java 11.0.9.1 (AdoptOpenJDK)"
)
public class ConditionalMethodWithSourceParameterAndValueMapperImpl implements ConditionalMethodWithSourceParameterAndValueMapper {

    @Override
    public BasicEmployee map(BasicEmployeeDto employee) {
        if ( employee == null ) {
            return null;
        }

        BasicEmployee basicEmployee = new BasicEmployee();

        if ( isPresent( employee, employee.getName() ) ) {
            basicEmployee.setName( employee.getName() );
        }

        return basicEmployee;
    }
}
