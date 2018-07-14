/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

import javax.xml.datatype.XMLGregorianCalendar;

public class XmlGregorianCalendarProperty {

    // CHECKSTYLE:OFF
    public XMLGregorianCalendar publicProp;
    // CHECKSTYLE:ON

    private XMLGregorianCalendar prop;

    public XMLGregorianCalendar getProp() {
        return prop;
    }

    public void setProp( XMLGregorianCalendar prop ) {
        this.prop = prop;
    }
}
