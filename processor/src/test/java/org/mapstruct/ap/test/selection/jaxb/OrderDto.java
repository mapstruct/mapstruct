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

/**
 * @author Sjaak Derksen
 */
public class OrderDto {

    private Long orderNumber1;
    private Long orderNumber2;
    private OrderShippingDetailsDto shippingDetails;

    public Long getOrderNumber1() {
        return orderNumber1;
    }

    public void setOrderNumber1(Long orderNumber1) {
        this.orderNumber1 = orderNumber1;
    }

    public Long getOrderNumber2() {
        return orderNumber2;
    }

    public void setOrderNumber2(Long orderNumber2) {
        this.orderNumber2 = orderNumber2;
    }

    public OrderShippingDetailsDto getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(OrderShippingDetailsDto shippingDetails) {
        this.shippingDetails = shippingDetails;
    }
}
