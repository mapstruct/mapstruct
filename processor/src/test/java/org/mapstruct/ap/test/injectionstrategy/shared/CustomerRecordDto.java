/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.shared;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Sjaak Derksen
 */
public class CustomerRecordDto {

    private XMLGregorianCalendar registrationDate;
    private CustomerDto customer;

    public XMLGregorianCalendar getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(XMLGregorianCalendar registrationDate) {
        this.registrationDate = registrationDate;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}
