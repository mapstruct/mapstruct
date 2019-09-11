/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.localdatetimetoxmlgregoriancalendarconversion;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Andrei Arlou
 */
public class Source {
    private XMLGregorianCalendar xmlGregorianCalendar;

    public XMLGregorianCalendar getXmlGregorianCalendar() {
        return xmlGregorianCalendar;
    }

    public void setXmlGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        this.xmlGregorianCalendar = xmlGregorianCalendar;
    }

}
