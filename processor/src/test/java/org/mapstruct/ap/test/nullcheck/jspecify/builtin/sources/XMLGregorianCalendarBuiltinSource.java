/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarBuiltinSource {

    // CHECKSTYLE:OFF
    public XMLGregorianCalendar publicXml;
    public String publicStr;
    // CHECKSTYLE:ON
    private XMLGregorianCalendar xml;
    private String str;

    public XMLGregorianCalendar getPublicXml() {
        return publicXml;
    }

    public void setPublicXml(XMLGregorianCalendar publicXml) {
        this.publicXml = publicXml;
    }

    public String getPublicStr() {
        return publicStr;
    }

    public void setPublicStr(String publicStr) {
        this.publicStr = publicStr;
    }

    public XMLGregorianCalendar getXml() {
        return xml;
    }

    public void setXml(XMLGregorianCalendar xml) {
        this.xml = xml;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
