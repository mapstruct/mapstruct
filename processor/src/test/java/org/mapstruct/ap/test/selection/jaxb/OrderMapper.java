/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.selection.jaxb;

import javax.xml.bind.JAXBElement;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.selection.jaxb.test1.ObjectFactory;
import org.mapstruct.ap.test.selection.jaxb.test1.OrderType;
import org.mapstruct.ap.test.selection.jaxb.test2.OrderShippingDetailsType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(uses = {
    ObjectFactory.class,
    org.mapstruct.ap.test.selection.jaxb.test2.ObjectFactory.class
})
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    // target 2 source methods
    public abstract OrderType targetToSource(OrderDto target);

    public abstract OrderShippingDetailsType dtoToOrderShippingDetailsType(OrderShippingDetailsDto target);

    // TODO, remove this method when #134 is fixed
    public JAXBElement<OrderShippingDetailsType> dtoToOrderShippingDetailsTypeJB(OrderShippingDetailsDto target) {
        ObjectFactory of1 = new ObjectFactory();
        return of1.createOrderTypeShippingDetails( INSTANCE.dtoToOrderShippingDetailsType( target ) );
    }
}
