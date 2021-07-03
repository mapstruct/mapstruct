/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2505;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-03T14:21:53+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class Issue2505MapperImpl implements Issue2505Mapper {

    @Override
    public Customer map(CustomerDTO value) {
        if ( value == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setStatus( value.getStatus() );

        return customer;
    }
}
