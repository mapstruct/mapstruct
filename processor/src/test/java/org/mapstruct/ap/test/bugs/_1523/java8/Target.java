/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1523.java8;

import javax.xml.datatype.XMLGregorianCalendar;

public class Target {
    private XMLGregorianCalendar value;
    private XMLGregorianCalendar value2;

    public XMLGregorianCalendar getValue() {
        return value;
    }

    public void setValue(XMLGregorianCalendar value) {
        this.value = value;
    }

    public XMLGregorianCalendar getValue2() {
        return value2;
    }

    public void setValue2(XMLGregorianCalendar value2) {
        this.value2 = value2;
    }
}
