/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding JAXB time full qualified class names for conversion registration
 */
public final class JaxbConstants {

    public static final String JAVAX_JAXB_ELEMENT_FQN = "javax.xml.bind.JAXBElement";
    public static final String JAKARTA_JAXB_ELEMENT_FQN = "jakarta.xml.bind.JAXBElement";

    private JaxbConstants() {
    }

}
