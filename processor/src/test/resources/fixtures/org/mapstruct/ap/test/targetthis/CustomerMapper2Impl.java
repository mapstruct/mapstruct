/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-02-12T11:25:08+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_192 (Azul Systems, Inc.)"
)
public class CustomerMapper2Impl implements CustomerMapper2 {

    @Override
    public Item map(ItemDTO item) {
        if ( item == null ) {
            return null;
        }

        Item item1 = new Item();

        if ( item.getId() != null ) {
            item1.setId( item.getId() );
        }
        item1.setStatus( item.getStatus() );

        return item1;
    }

    @Override
    public CustomerItem map(CustomerDTO customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerItem customerItem = new CustomerItem();

        if ( customer.getName() != null ) {
            customerItem.setName( customer.getName() );
        }
        customerItem.setLevel( customer.getLevel() );

        return customerItem;
    }

    @Override
    public CustomerItem mapNameOnly(CustomerDTO customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerItem customerItem = new CustomerItem();

        if ( customer.getName() != null ) {
            customerItem.setName( customer.getName() );
        }
        customerItem.setLevel( customer.getLevel() );

        return customerItem;
    }

    @Override
    public CustomerDTO inverseMap(CustomerItem customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        if ( customer.getName() != null ) {
            customerDTO.setName( customer.getName() );
        }
        customerDTO.setLevel( customer.getLevel() );

        return customerDTO;
    }
}
