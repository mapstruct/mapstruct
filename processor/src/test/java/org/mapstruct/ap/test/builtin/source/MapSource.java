/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.source;

import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Sjaak Derksen
 */
public class MapSource {

    // CHECKSTYLE:OFF
    public Map<JAXBElement<String>, XMLGregorianCalendar> publicExample;
    // CHECKSTYLE:ON

    private Map<JAXBElement<String>, XMLGregorianCalendar> example;

    public Map<JAXBElement<String>, XMLGregorianCalendar> getExample() {
        return example;
    }

    public void setExample( Map<JAXBElement<String>, XMLGregorianCalendar> example ) {
        this.example = example;
    }
}
