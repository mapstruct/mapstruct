/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

import jakarta.xml.bind.JAXBElement;

public class JakartaJaxbElementProperty {

    // CHECKSTYLE:OFF
    public JAXBElement<String> publicProp;
    // CHECKSTYLE:ON

    private JAXBElement<String> prop;

    public JAXBElement<String> getProp() {
        return prop;
    }

    public void setProp( JAXBElement<String> prop ) {
        this.prop = prop;
    }
}
