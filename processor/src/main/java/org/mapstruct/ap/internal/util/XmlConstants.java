/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding JAXB time full qualified class names for conversion registration
 *
 * @author Filip Hrisafov
 */
public final class XmlConstants {

    // CHECKSTYLE:OFF
    public static final String JAVAX_XML_XML_GREGORIAN_CALENDAR = "javax.xml.datatype.XMLGregorianCalendar";
    public static final String JAVAX_XML_DATATYPE_CONFIGURATION_EXCEPTION = "javax.xml.datatype.DatatypeConfigurationException";
    public static final String JAVAX_XML_DATATYPE_FACTORY = "javax.xml.datatype.DatatypeFactory";
    public static final String JAVAX_XML_DATATYPE_CONSTANTS = "javax.xml.datatype.DatatypeConstants";
    // CHECKSTYLE:ON

    private XmlConstants() {
    }

}
