/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.itest.supertypegeneration.usage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mapstruct.itest.supertypegeneration.usage.Order;
import org.mapstruct.itest.supertypegeneration.usage.OrderDto;
import org.mapstruct.itest.supertypegeneration.usage.OrderMapper;

/**
 * Integration test for using MapStruct with another annotation processor that generates super-types of mapping source
 * and target types.
 *
 * @author Gunnar Morling
 */
public class GeneratedBasesTest {

	@Test
	public void considersPropertiesOnGeneratedSourceAndTargetTypes() {
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
