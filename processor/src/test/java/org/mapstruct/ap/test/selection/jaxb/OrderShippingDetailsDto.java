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
public class OrderShippingDetailsDto {

    private String orderShippedFrom;
    private String orderShippedTo;

    public String getOrderShippedFrom() {
        return orderShippedFrom;
    }

    public void setOrderShippedFrom(String orderShippedFrom) {
        this.orderShippedFrom = orderShippedFrom;
    }

    public String getOrderShippedTo() {
        return orderShippedTo;
    }

    public void setOrderShippedTo(String orderShippedTo) {
        this.orderShippedTo = orderShippedTo;
    }
}
