/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
