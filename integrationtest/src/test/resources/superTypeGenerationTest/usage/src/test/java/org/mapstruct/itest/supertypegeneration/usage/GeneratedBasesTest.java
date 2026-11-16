/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.supertypegeneration.usage;

import org.junit.jupiter.api.Test;
import org.mapstruct.itest.supertypegeneration.usage.Order;
import org.mapstruct.itest.supertypegeneration.usage.OrderDto;
import org.mapstruct.itest.supertypegeneration.usage.OrderMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test for using MapStruct with another annotation processor that generates super-types of mapping source
 * and target types.
 *
 * @author Gunnar Morling
 */
class GeneratedBasesTest {

	@Test
	void considersPropertiesOnGeneratedSourceAndTargetTypes() {
		Order order = new Order();
		order.setItem( "my item" );
		order.setBaseName2( "my base name 2" );
		order.setBaseName1( "my base name 1" );

		OrderDto dto = OrderMapper.INSTANCE.orderToDto( order );
		assertEquals( order.getItem(), dto.getItem() );
		assertEquals( order.getBaseName2(), dto.getBaseName2() );
		assertEquals( order.getBaseName1(), dto.getBaseName1() );

		// Let's make sure several mappers can be generated
		dto = AnotherOrderMapper.INSTANCE.orderToDto( order );
		assertEquals( order.getItem(), dto.getItem() );
		assertEquals( order.getBaseName2(), dto.getBaseName2() );
		assertEquals( order.getBaseName1(), dto.getBaseName1() );
	}
}
