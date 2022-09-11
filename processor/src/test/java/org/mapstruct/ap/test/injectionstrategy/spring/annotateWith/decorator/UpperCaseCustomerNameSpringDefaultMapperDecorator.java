/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith.decorator;

import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UpperCaseCustomerNameSpringDefaultMapperDecorator
    implements CustomerSpringComponentQualifiedMapper {

    @Autowired
    @Qualifier("delegate")
    private CustomerSpringDefaultMapper delegate;

    @Override
    public CustomerDto asTarget(CustomerEntity customerEntity) {
        CustomerDto customerDto = delegate.asTarget( customerEntity );
        // Generate a side effect to verify that the component is actually being used
        // by the conversion process
        customerDto.setName( customerDto.getName().toUpperCase() );
        return customerDto;
    }
}
