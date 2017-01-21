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
package org.mapstruct.itest.targettypegeneration.usage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Integration test for using MapStruct with another annotation processor that generates the target type of a mapping
 * method.
 *
 * @author Gunnar Morling
 */
public class GeneratedTargetTypeTest {

    @Test
    public void considersPropertiesOnGeneratedSourceAndTargetTypes() {
        Order order = new Order();
        order.setItem( "my item" );

        OrderDto dto = OrderMapper.INSTANCE.orderToDto( order );
        assertEquals( order.getItem(), dto.getItem() );
    }
}
