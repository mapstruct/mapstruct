/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.source;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Sjaak Derksen
 */
public class IterableSource {

    // CHECKSTYLE:OFF
    public List<XMLGregorianCalendar> publicDates;
    // CHECKSTYLE:ON

    private List<XMLGregorianCalendar> dates;

    public List<XMLGregorianCalendar> getDates() {
        return dates;
    }

    public void setDates( List<XMLGregorianCalendar> dates ) {
        this.dates = dates;
    }
}
