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

    public static final String JAXB_ELEMENT_FQN = "javax.xml.bind.JAXBElement";
    private static final boolean IS_JAXB_ELEMENT_PRESENT = ClassUtils.isPresent(
        JAXB_ELEMENT_FQN,
        JaxbConstants.class.getClassLoader()
    );

    private JaxbConstants() {
    }

    /**
     * @return {@code true} if {@link javax.xml.bind.JAXBElement} is present, {@code false} otherwise
     */
    public static boolean isJaxbElementPresent() {
        return IS_JAXB_ELEMENT_PRESENT;
    }
}
