/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.usestypegeneration.usage;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for using MapStruct with another annotation processor that generates the other mappers for uses
 *
 * @author Filip Hrisafov
 */
public class GeneratedUsesTypeTest {

    @Test
    public void considersPropertiesOnGeneratedSourceAndTargetTypes() {
        Order order = new Order();
        order.setItem( "my item" );

        OrderDto dto = OrderMapper.INSTANCE.orderToDto( order );
        assertThat( dto.getItem() ).isEqualTo( "MY ITEM" );
    }
}
