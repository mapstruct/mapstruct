/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

import java.util.List;

import javax.xml.bind.JAXBElement;

public class JaxbElementListProperty {

    // CHECKSTYLE:OFF
    public List<JAXBElement<String>> publicProp;
    // CHECKSTYLE:ON

    private List<JAXBElement<String>> prop;

    public List<JAXBElement<String>> getProp() {
        return prop;
    }

    public void setProp( List<JAXBElement<String>> prop ) {
        this.prop = prop;
    }

}
