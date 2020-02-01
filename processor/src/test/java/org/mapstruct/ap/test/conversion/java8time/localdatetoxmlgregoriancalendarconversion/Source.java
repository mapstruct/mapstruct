/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.localdatetoxmlgregoriancalendarconversion;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Andreas Gudian
 */
public class Source {
    private XMLGregorianCalendar date;

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

}
