/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.faultyAstModifyingProcessor.usage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for using MapStruct with a faulty AstModifyingProcessor (i.e. when the processor cannot be loaded)
 *
 * @author Filip Hrisafov
 */
class FaultyAstModifyingTestTest {

    @Test
    void testMapping() {
        Order order = new Order();
        order.setItem( "my item" );

        OrderDto dto = OrderMapper.INSTANCE.orderToDto( order );
        assertThat( dto.getItem() ).isEqualTo( "my item" );
    }
}
