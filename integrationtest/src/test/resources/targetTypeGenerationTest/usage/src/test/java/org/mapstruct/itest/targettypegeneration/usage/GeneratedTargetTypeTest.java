/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.targettypegeneration.usage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Integration test for using MapStruct with another annotation processor that generates the target type of a mapping
 * method.
 *
 * @author Gunnar Morling
 */
class GeneratedTargetTypeTest {

    @Test
    void considersPropertiesOnGeneratedSourceAndTargetTypes() {
        Order order = new Order();
        order.setItem( "my item" );

        OrderDto dto = OrderMapper.INSTANCE.orderToDto( order );
        assertEquals( order.getItem(), dto.getItem() );
    }
}
