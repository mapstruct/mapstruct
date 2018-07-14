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

    public static final String JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR = "javax.xml.datatype.XMLGregorianCalendar";
    private static final boolean IS_XML_GREGORIAN_CALENDAR_PRESENT = ClassUtils.isPresent(
        JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR,
        XmlConstants.class.getClassLoader()
    );

    private XmlConstants() {
    }

    /**
     * @return {@code true} if the {@link javax.xml.datatype.XMLGregorianCalendar} is present, {@code false} otherwise
     */
    public static boolean isXmlGregorianCalendarPresent() {
        return IS_XML_GREGORIAN_CALENDAR_PRESENT;
    }
}
