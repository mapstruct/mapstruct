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
package org.mapstruct.itest.jaxb;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.jaxb.xsd.test1.OrderDetailsType;
import org.mapstruct.itest.jaxb.xsd.test1.OrderType;
import org.mapstruct.itest.jaxb.xsd.test2.OrderStatusType;
import org.mapstruct.itest.jaxb.xsd.test2.ShippingAddressType;


/**
 * @author Sjaak Derksen
 */
@Mapper(uses = {
    org.mapstruct.itest.jaxb.xsd.test1.ObjectFactory.class,
    org.mapstruct.itest.jaxb.xsd.test2.ObjectFactory.class,
    JaxbMapper.class
})
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    // source 2 target methods
    OrderDto sourceToTarget(OrderType source);

    OrderDetailsDto detailsToDto(OrderDetailsType source);

    OrderStatusDto statusToDto(OrderStatusType source);

    ShippingAddressDto shippingAddressToDto(ShippingAddressType source);

    // target 2 source methods
    OrderType targetToSource(OrderDto target);

    OrderDetailsType dtoToDetails(OrderDetailsDto target);

    OrderStatusType dtoToStatus(OrderStatusDto target);

    ShippingAddressType dtoToShippingAddress(ShippingAddressDto source);
}
