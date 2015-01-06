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
package org.mapstruct.ap.test.nestedmethodcall;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Sjaak Derksen
 */
public class OrderType {

    private JAXBElement<Long> orderNumber;
    private JAXBElement<OrderDetailsType> orderDetails;
    private List<JAXBElement<XMLGregorianCalendar>> dates;


    public JAXBElement<Long> getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(JAXBElement<Long> value) {
        this.orderNumber = value;
    }

    public JAXBElement<OrderDetailsType> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(JAXBElement<OrderDetailsType> value) {
        this.orderDetails = value;
    }

    public List<JAXBElement<XMLGregorianCalendar>> getDates() {
        return dates;
    }

    public void setDates(List<JAXBElement<XMLGregorianCalendar>> dates) {
        this.dates = dates;
    }

}
